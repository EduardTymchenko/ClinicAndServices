package com.example.clinic.sevices.impl;

import org.springframework.stereotype.Service;
import com.example.clinic.dtos.ClinicDto;
import com.example.clinic.exeption.ResourceNotFoundException;
import com.example.clinic.repositories.ClinicRepository;
import com.example.clinic.repositories.ServiceRepository;
import com.example.clinic.sevices.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;
    private final ServiceRepository serviceRepository;


    @Override
    public ClinicDto create(ClinicDto clinicDto) {
        return null;
    }

    @Override
    public List<ClinicDto> getAll() {
        return null;
    }

    @Override
    public ClinicDto getById(long id) {
        return clinicRepository.getById(id)
                .map(clinic -> new ClinicDto(clinic, serviceRepository.getAllByClinicId(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Clinic with id = " + id + " is not fond"));
    }

    @Override
    public ClinicDto update(ClinicDto clinicDto) {
        return null;
    }

    @Override
    public void delete(long id) {
        serviceRepository.deleteAllByClinicId(id);
        clinicRepository.getById(id);

    }
}
