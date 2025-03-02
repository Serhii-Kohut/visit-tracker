package com.doctorvisits.visittracker.service;

import com.doctorvisits.visittracker.dto.CreateVisitRequestDto;
import com.doctorvisits.visittracker.entity.Visit;

public interface VisitService {
    Visit createVisit(CreateVisitRequestDto request);
}
