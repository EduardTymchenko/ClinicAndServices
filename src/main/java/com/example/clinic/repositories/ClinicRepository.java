package com.example.clinic.repositories;

import com.example.clinic.domain.Clinic;

import java.util.List;

public interface ClinicRepository {

    Clinic create(Clinic clinic);

    List<Clinic> getAll();

    Clinic getById(long id);

    Clinic update(Clinic clinic);

    void delete (long id);

    int getNumberOfClinics();




}
