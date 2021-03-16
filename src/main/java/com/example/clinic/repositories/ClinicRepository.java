package com.example.clinic.repositories;

import com.example.clinic.domain.Clinic;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClinicRepository {

    Optional<Clinic>  create(Clinic clinic);

    List<Clinic> getAll();

    Optional<Clinic> getById(long id);

    Optional<Clinic> update(Clinic clinic);

    void delete (long id);

    int getNumberOfClinics();

    Set<String> getNamesClinicInsurance(boolean hasInsurance);

    List<Clinic> getAllByText(String searchText, Integer pageNumber, Integer pageSize);

}
