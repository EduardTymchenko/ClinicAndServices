package com.example.clinic.repositories;

import com.example.clinic.domain.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClinicRepository extends JpaRepository<Clinic,Long> {
}
