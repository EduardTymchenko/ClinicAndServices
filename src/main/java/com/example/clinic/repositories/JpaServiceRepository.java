package com.example.clinic.repositories;

import com.example.clinic.domain.Clinic;
import com.example.clinic.domain.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaServiceRepository extends JpaRepository<Service,Long> {
    List<Service> findAllByClinic(Clinic clinic);
    void deleteAllByClinic(Clinic clinic);

}
