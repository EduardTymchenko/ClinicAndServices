package com.example.clinic.sevices;

import com.example.clinic.dtos.ServiceDto;

import java.util.List;

public interface ServiceService {

    ServiceDto create(ServiceDto service, long idClinic);

    List<ServiceDto> getAll();

    ServiceDto getById(long id);

    ServiceDto update(ServiceDto service, long idClinic);

    void delete(long id);

    List<ServiceDto> getAllByText(String searchText, int pageNumber, int pageSize);
}
