package com.doctorvisits.visittracker.controller;

import com.doctorvisits.visittracker.dto.CreateVisitRequestDto;
import com.doctorvisits.visittracker.entity.Visit;
import com.doctorvisits.visittracker.service.VisitService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VisitController {

    VisitService visitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Visit createVisit(@RequestBody CreateVisitRequestDto request) {
        return visitService.createVisit(request);
    }
}
