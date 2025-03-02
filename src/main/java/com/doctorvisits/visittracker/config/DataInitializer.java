package com.doctorvisits.visittracker.config;

import com.doctorvisits.visittracker.entity.Doctor;
import com.doctorvisits.visittracker.entity.Patient;
import com.doctorvisits.visittracker.entity.Visit;
import com.doctorvisits.visittracker.repository.DoctorRepository;
import com.doctorvisits.visittracker.repository.PatientRepository;
import com.doctorvisits.visittracker.repository.VisitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataInitializer implements CommandLineRunner {

    DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    VisitRepository visitRepository;

    @Override
    public void run(String... args) {
        if (doctorRepository.count() == 0 && patientRepository.count() == 0 && visitRepository.count() == 0) {
            Doctor doctor1 = Doctor.builder()
                    .firstName("Олена")
                    .lastName("Коваленко")
                    .timeZone("Europe/Kiev")
                    .build();
            Doctor doctor2 = Doctor.builder()
                    .firstName("Ігор")
                    .lastName("Петренко")
                    .timeZone("Europe/Kiev")
                    .build();
            Doctor doctor3 = Doctor.builder()
                    .firstName("Марія")
                    .lastName("Сидоренко")
                    .timeZone("America/New_York")
                    .build();
            doctorRepository.saveAll(List.of(doctor1, doctor2, doctor3));

            Patient patient1 = Patient.builder()
                    .firstName("Андрій")
                    .lastName("Мельник")
                    .build();
            Patient patient2 = Patient.builder()
                    .firstName("Софія")
                    .lastName("Левицька")
                    .build();
            Patient patient3 = Patient.builder()
                    .firstName("Василь")
                    .lastName("Гнатюк")
                    .build();
            Patient patient4 = Patient.builder()
                    .firstName("Юлія")
                    .lastName("Бондар")
                    .build();
            patientRepository.saveAll(List.of(patient1, patient2, patient3, patient4));

            Visit visit1 = Visit.builder()
                    .startDateTime(ZonedDateTime.parse("2025-03-01T09:00:00+02:00"))
                    .endDateTime(ZonedDateTime.parse("2025-03-01T09:30:00+02:00"))
                    .patient(patient1)
                    .doctor(doctor1)
                    .build();
            Visit visit2 = Visit.builder()
                    .startDateTime(ZonedDateTime.parse("2025-03-01T10:00:00+02:00"))
                    .endDateTime(ZonedDateTime.parse("2025-03-01T10:30:00+02:00"))
                    .patient(patient2)
                    .doctor(doctor1)
                    .build();
            Visit visit3 = Visit.builder()
                    .startDateTime(ZonedDateTime.parse("2025-03-02T14:00:00+02:00"))
                    .endDateTime(ZonedDateTime.parse("2025-03-02T14:30:00+02:00"))
                    .patient(patient3)
                    .doctor(doctor2)
                    .build();
            Visit visit4 = Visit.builder()
                    .startDateTime(ZonedDateTime.parse("2025-03-03T08:00:00-05:00"))
                    .endDateTime(ZonedDateTime.parse("2025-03-03T08:30:00-05:00"))
                    .patient(patient1)
                    .doctor(doctor3)
                    .build();
            Visit visit5 = Visit.builder()
                    .startDateTime(ZonedDateTime.parse("2025-03-04T15:00:00+02:00"))
                    .endDateTime(ZonedDateTime.parse("2025-03-04T15:30:00+02:00"))
                    .patient(patient4)
                    .doctor(doctor2)
                    .build();
            Visit visit6 = Visit.builder()
                    .startDateTime(ZonedDateTime.parse("2025-03-05T11:00:00-05:00"))
                    .endDateTime(ZonedDateTime.parse("2025-03-05T11:30:00-05:00"))
                    .patient(patient2)
                    .doctor(doctor3)
                    .build();
            visitRepository.saveAll(List.of(visit1, visit2, visit3, visit4, visit5, visit6));
        }
    }
}
