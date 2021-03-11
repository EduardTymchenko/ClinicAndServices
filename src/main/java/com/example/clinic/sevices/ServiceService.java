package com.example.clinic.sevices;

import com.example.clinic.domain.Service;
import com.example.clinic.dtos.ServiceDto;
import java.util.List;

public interface ServiceService {

    ServiceDto create(ServiceDto service);

    List<Service> getAll();

    ServiceDto getById(long id);

    ServiceDto update(ServiceDto service);

    void delete(long id);
}
