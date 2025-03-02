package com.doctorvisits.visittracker.repository;

import com.doctorvisits.visittracker.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
