package com.example.clinic.sevices.impl;

import com.example.clinic.dtos.ServiceDto;
import com.example.clinic.exeption.ResourceNotFoundException;
import com.example.clinic.repositories.ServiceRepository;
import com.example.clinic.sevices.ServiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public ServiceDto create(ServiceDto service) {
        return null;
    }

    @Override
    public List<com.example.clinic.domain.Service> getAll() {
        return null;
    }

    @Override
    public ServiceDto getById(long id) {
        return serviceRepository.getById(id)
                .map(ServiceDto::new)
                .orElseThrow(()-> new ResourceNotFoundException("Service with id = " + id + " is not found"));
    }

    @Override
    public ServiceDto update(ServiceDto service) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
