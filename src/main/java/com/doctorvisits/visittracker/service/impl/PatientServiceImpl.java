package com.doctorvisits.visittracker.service.impl;

import com.doctorvisits.visittracker.dto.DoctorDataDto;
import com.doctorvisits.visittracker.dto.PatientDataDto;
import com.doctorvisits.visittracker.dto.PatientListResponseDto;
import com.doctorvisits.visittracker.dto.VisitDataDto;
import com.doctorvisits.visittracker.entity.Doctor;
import com.doctorvisits.visittracker.entity.Patient;
import com.doctorvisits.visittracker.entity.Visit;
import com.doctorvisits.visittracker.repository.PatientRepository;
import com.doctorvisits.visittracker.repository.VisitRepository;
import com.doctorvisits.visittracker.service.PatientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientServiceImpl implements PatientService {

    PatientRepository patientRepository;
    VisitRepository visitRepository;

    @Override
    @Transactional(readOnly = true)
    public PatientListResponseDto getPatients(int page, int size, String search, String doctorIds) {
        List<Long> doctorIdList = parseDoctorIds(doctorIds);
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        Page<Patient> patientsPage = patientRepository.findPatientsWithFilters(
                search != null && !search.isBlank() ? search : null,
                doctorIdList,
                pageRequest
        );

        List<Patient> patients = patientsPage.getContent();
        if (patients.isEmpty()) {
            return PatientListResponseDto.builder()
                    .data(Collections.emptyList())
                    .count(0L)
                    .build();
        }

        List<Visit> allVisits = visitRepository.findVisitsByPatients(patients);
        Map<Patient, List<Visit>> visitsByPatient = allVisits.stream()
                .collect(Collectors.groupingBy(Visit::getPatient));

        List<Long> allDoctorIds = allVisits.stream()
                .map(visit -> visit.getDoctor().getId())
                .distinct()
                .collect(Collectors.toList());

        List<Object[]> doctorPatientCounts = visitRepository.countDistinctPatientsByDoctors(allDoctorIds);
        Map<Long, Long> doctorPatientCountMap = doctorPatientCounts.stream()
                .collect(Collectors.toMap(row -> (Long) row[0], row -> (Long) row[1]));

        List<PatientDataDto> patientData = patients.stream()
                .map(patient -> {
                    List<Visit> patientVisits = visitsByPatient.getOrDefault(patient, Collections.emptyList());
                    List<Visit> lastVisits = patientVisits.stream()
                            .filter(visit -> doctorIdList == null || doctorIdList.contains(visit.getDoctor().getId()))
                            .collect(Collectors.groupingBy(
                                    visit -> visit.getDoctor().getId(),
                                    Collectors.maxBy(Comparator.comparing(Visit::getId))
                            ))
                            .values().stream()
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .collect(Collectors.toList());

                    List<VisitDataDto> visitDtos = lastVisits.stream()
                            .map(visit -> {
                                Doctor doctor = visit.getDoctor();
                                DoctorDataDto doctorDto = DoctorDataDto.builder()
                                        .firstName(doctor.getFirstName())
                                        .lastName(doctor.getLastName())
                                        .totalPatients(doctorPatientCountMap.getOrDefault(doctor.getId(), 0L))
                                        .build();
                                return VisitDataDto.builder()
                                        .start(visit.getStartDateTime().toString())
                                        .end(visit.getEndDateTime().toString())
                                        .doctor(doctorDto)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return PatientDataDto.builder()
                            .firstName(patient.getFirstName())
                            .lastName(patient.getLastName())
                            .lastVisits(visitDtos)
                            .build();
                })
                .collect(Collectors.toList());

        return PatientListResponseDto.builder()
                .data(patientData)
                .count(patientsPage.getTotalElements())
                .build();
    }

    private List<Long> parseDoctorIds(String doctorIds) {
        if (doctorIds == null || doctorIds.isBlank()) {
            return null;
        }
        try {
            return Arrays.stream(doctorIds.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid doctorIds format: " + doctorIds);
        }
    }
}

