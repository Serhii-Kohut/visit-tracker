package com.doctorvisits.visittracker.repository;

import com.doctorvisits.visittracker.entity.Doctor;
import com.doctorvisits.visittracker.entity.Patient;
import com.doctorvisits.visittracker.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT v FROM Visit v WHERE v.patient IN :patients")
    List<Visit> findVisitsByPatients(@Param("patients") List<Patient> patients);

    @Query("SELECT v.doctor.id, COUNT(DISTINCT v.patient) FROM Visit v WHERE v.doctor.id IN :doctorIds GROUP BY v.doctor.id")
    List<Object[]> countDistinctPatientsByDoctors(@Param("doctorIds") List<Long> doctorIds);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM Visit v WHERE v.doctor = :doctor " +
            "AND v.startDateTime < :end AND v.endDateTime > :start")
    boolean existsByDoctorAndTimeOverlap(Doctor doctor, ZonedDateTime start, ZonedDateTime end);
}