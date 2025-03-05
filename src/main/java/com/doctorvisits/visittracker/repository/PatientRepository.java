package com.doctorvisits.visittracker.repository;

import com.doctorvisits.visittracker.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT DISTINCT p FROM Patient p " +
            "WHERE (:search IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :search, '%'))) " +
            "AND (:doctorIds IS NULL OR EXISTS (SELECT v FROM Visit v WHERE v.patient = p AND v.doctor.id IN :doctorIds))")
    Page<Patient> findPatientsWithFilters(
            @Param("search") String search,
            @Param("doctorIds") List<Long> doctorIds,
            Pageable pageable);
}

