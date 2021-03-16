package com.example.clinic.sevices.impl;

import com.example.clinic.dtos.ServiceDto;
import com.example.clinic.exeption.ResourceNotCreatedException;
import com.example.clinic.exeption.ResourceNotFoundException;
import com.example.clinic.exeption.ResourceNotUpdateException;
import com.example.clinic.repositories.ClinicRepository;
import com.example.clinic.repositories.ServiceRepository;
import com.example.clinic.sevices.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;
    private final ClinicRepository clinicRepository;

    @Override
    public ServiceDto create(ServiceDto serviceDto, long idClinic) {
        if (!existsClinicById(idClinic)) throw new ResourceNotCreatedException("Service was not created. Clinic with id = " +
                idClinic + " is not exists");
        return ServiceDto.toDto(serviceRepository.create(ServiceDto.toDomain(serviceDto, idClinic))
                .orElseThrow(() -> new ResourceNotCreatedException("Service was not created for Clinic id = " + idClinic)));
    }

    @Override
    public List<ServiceDto> getAll() {
        return serviceRepository.getAll().stream().map(ServiceDto::new).collect(Collectors.toList());
    }

    @Override
    public ServiceDto getById(long id) {
        return serviceRepository.getById(id)
                .map(ServiceDto::new)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id = " + id + " is not found"));
    }

    @Override
    public ServiceDto update(ServiceDto serviceDto, long idClinic) {
        if (!existsClinicById(idClinic)) throw new ResourceNotUpdateException("Service was not update. Clinic with id = " +
                idClinic + " is not found");
        return ServiceDto.toDto(serviceRepository.update(ServiceDto.toDomain(serviceDto, idClinic))
                .orElseThrow(() -> new ResourceNotUpdateException("Service was not update for Clinic id = " + idClinic)));
    }

    @Override
    public void delete(long id) {
        serviceRepository.delete(id);
    }

    @Override
    public List<ServiceDto> getAllByText(String searchText, Integer pageNumber, Integer pageSize) {
        return serviceRepository.getAllByText(searchText, pageNumber, pageSize)
                .stream()
                .map(ServiceDto::new)
                .collect(Collectors.toList());
    }

    private boolean existsClinicById (long idClinic){
        return clinicRepository.getById(idClinic).isPresent();
    }
}
