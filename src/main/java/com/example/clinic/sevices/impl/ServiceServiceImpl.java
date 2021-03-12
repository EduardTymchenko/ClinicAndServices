package com.example.clinic.sevices.impl;

import com.example.clinic.dtos.ServiceDto;
import com.example.clinic.exeption.ResourceNotFoundException;
import com.example.clinic.repositories.ServiceRepository;
import com.example.clinic.sevices.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public ServiceDto create(ServiceDto service) {
        return null;
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
    public ServiceDto update(ServiceDto service) {
        return null;
    }

    @Override
    public void delete(long id) {
        serviceRepository.delete(id);
    }
}
