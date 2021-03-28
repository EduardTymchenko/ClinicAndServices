package com.example.clinic.sevices.impl;

import com.example.clinic.domain.Clinic;
import com.example.clinic.dtos.ServiceDto;
import com.example.clinic.exeption.ResourceNotCreatedException;
import com.example.clinic.exeption.ResourceNotFoundException;
import com.example.clinic.exeption.ResourceNotUpdateException;
import com.example.clinic.repositories.JpaClinicRepository;
import com.example.clinic.repositories.JpaServiceRepository;
import com.example.clinic.sevices.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final JpaServiceRepository serviceRepository;
    private final JpaClinicRepository clinicRepository;

    @Override
    public ServiceDto create(ServiceDto serviceDto, long idClinic) {
        Clinic clinic = clinicRepository.findById(idClinic).orElseThrow(() -> new ResourceNotCreatedException("Service was not created. Clinic with id = " +
                idClinic + " is not exists"));
        com.example.clinic.domain.Service newService = ServiceDto.toDomain(serviceDto);
        //todo
        if (serviceRepository.findAllByClinic(clinic).contains(newService)) throw new ResourceNotCreatedException("Service was not created for Clinic id = " + idClinic);
        return ServiceDto.toDto(serviceRepository.save(newService));
    }

    @Override
    public List<ServiceDto> getAll() {
        return serviceRepository.findAll().stream().map(ServiceDto::new).collect(Collectors.toList());
    }

    @Override
    public ServiceDto getById(long id) {
        return serviceRepository.findById(id)
                .map(ServiceDto::new)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id = " + id + " is not found"));
    }

    @Override
    public ServiceDto update(ServiceDto serviceDto, long idClinic) {
        Clinic clinic = clinicRepository.findById(idClinic).orElseThrow(() -> new ResourceNotUpdateException("Service was not update. Clinic with id = " +
                idClinic + " is not found"));

        serviceRepository.

        serviceRepository.findById(serviceDto.getId()).map(service->return serviceRepository.save(ServiceDto.toDomain(serviceDto))
                .orElseThrow(() -> new ResourceNotUpdateException("Service was not update for Clinic id = " + idClinic)));
//        return ServiceDto.toDto(serviceRepository.save( idClinic))

    }

    @Override
    public void delete(long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public List<ServiceDto> getAllByText(String searchText, Integer pageNumber, Integer pageSize) {
        return serviceRepository.getAllByText(searchText, pageNumber, pageSize)
                .stream()
                .map(ServiceDto::new)
                .collect(Collectors.toList());
    }

    private Optional<Clinic> existsClinicById (long idClinic){
        return clinicRepository.findById(idClinic);
    }
}
