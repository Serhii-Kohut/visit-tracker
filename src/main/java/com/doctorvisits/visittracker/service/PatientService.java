package com.doctorvisits.visittracker.service;

import com.doctorvisits.visittracker.dto.PatientListResponseDto;

import java.util.List;

public interface PatientService {
    PatientListResponseDto getPatients(int page, int size, String search, String doctorIds);
}
