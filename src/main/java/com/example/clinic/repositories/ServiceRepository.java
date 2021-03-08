package com.example.clinic.repositories;


import com.example.clinic.domain.Clinic;
import com.example.clinic.domain.Service;

import java.util.List;

public interface ServiceRepository {

    Service create(Service service);

    List<Service> getAll();

    Service getById(long id);

    Service update(Service service);

    void delete (long id);

    int getNumberOfServices();

}
