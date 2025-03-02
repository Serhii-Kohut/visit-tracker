package com.doctorvisits.visittracker.controller;

import com.doctorvisits.visittracker.dto.PatientListResponseDto;
import com.doctorvisits.visittracker.service.PatientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PatientController {

    PatientService patientService;

    @GetMapping
    public PatientListResponseDto getPatients(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(name = "doctorIds", required = false) String doctorIds) {
        return patientService.getPatients(page, size, search, doctorIds);
    }
}
