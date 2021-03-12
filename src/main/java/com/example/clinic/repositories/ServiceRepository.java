package com.example.clinic.repositories;

import com.example.clinic.domain.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ServiceRepository {

    Optional<Service> create(Service service);

    List<Service> getAll();

    Optional<Service> getById(long id);

    Optional<Service> update(Service service);

    void delete (long id);

    int getNumberOfServices();

    Set<String> getNamesByFee(float minFee, float maxFee);

    List<Service> getAllByClinicId(long id);

    void deleteAllByClinicId(long id);

}
