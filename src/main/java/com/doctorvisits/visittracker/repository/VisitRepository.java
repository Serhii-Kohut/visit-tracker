package com.doctorvisits.visittracker.repository;

import com.doctorvisits.visittracker.entity.Doctor;
import com.doctorvisits.visittracker.entity.Patient;
import com.doctorvisits.visittracker.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT v FROM Visit v WHERE v.patient = :patient " +
            "AND (:doctorIds IS NULL OR v.doctor.id IN :doctorIds) " +
            "AND v.id IN (SELECT MAX(v2.id) FROM Visit v2 WHERE v2.patient = :patient GROUP BY v2.doctor)")
    List<Visit> findLastVisitsByPatient(Patient patient, List<Long> doctorIds);

    @Query("SELECT COUNT(DISTINCT v.patient) FROM Visit v WHERE v.doctor = :doctor")
    int countDistinctPatientsByDoctor(Doctor doctor);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM Visit v WHERE v.doctor = :doctor " +
            "AND v.startDateTime < :end AND v.endDateTime > :start")
    boolean existsByDoctorAndTimeOverlap(Doctor doctor, ZonedDateTime start, ZonedDateTime end);
}