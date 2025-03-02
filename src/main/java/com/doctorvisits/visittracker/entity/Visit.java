package com.doctorvisits.visittracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Table(name = "visits")
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Visit extends BaseEntity {

    ZonedDateTime startDateTime;
    ZonedDateTime endDateTime;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    Doctor doctor;
}
