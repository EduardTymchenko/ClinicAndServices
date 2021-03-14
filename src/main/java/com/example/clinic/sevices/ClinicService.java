package com.example.clinic.sevices;

import com.example.clinic.dtos.ClinicDto;

import java.util.List;

public interface ClinicService {

    ClinicDto create(ClinicDto clinicDto);

    List<ClinicDto> getAll();

    ClinicDto getById(long id);

    ClinicDto update(ClinicDto clinicDto);

    void delete(long id);

    List<ClinicDto> getAllByText(String searchText, int pageNumber, int pageSize);
}
