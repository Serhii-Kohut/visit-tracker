package com.doctorvisits.visittracker.service.impl;

import com.doctorvisits.visittracker.dto.CreateVisitRequestDto;
import com.doctorvisits.visittracker.entity.Doctor;
import com.doctorvisits.visittracker.entity.Patient;
import com.doctorvisits.visittracker.entity.Visit;
import com.doctorvisits.visittracker.repository.DoctorRepository;
import com.doctorvisits.visittracker.repository.PatientRepository;
import com.doctorvisits.visittracker.repository.VisitRepository;
import com.doctorvisits.visittracker.service.VisitService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VisitServiceImpl implements VisitService {

    VisitRepository visitRepository;
    PatientRepository patientRepository;
    DoctorRepository doctorRepository;

    @Transactional
    public Visit createVisit(CreateVisitRequestDto request) {
        // Знаходимо пацієнта та лікаря
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + request.getPatientId()));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + request.getDoctorId()));

        // Парсимо час із таймзоною лікаря
        ZonedDateTime start = ZonedDateTime.parse(request.getStart(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        ZonedDateTime end = ZonedDateTime.parse(request.getEnd(), DateTimeFormatter.ISO_ZONED_DATE_TIME);

        // Перевірка валідності часу
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }

        // Перевірка перетинів для лікаря
        if (visitRepository.existsByDoctorAndTimeOverlap(doctor, start, end)) {
            throw new IllegalArgumentException("Doctor is already booked at this time");
        }

        // Створюємо візит
        Visit visit = Visit.builder()
                .startDateTime(start)
                .endDateTime(end)
                .patient(patient)
                .doctor(doctor)
                .build();

        return visitRepository.save(visit);
    }
}
