package com.example.clinic.sevices.impl;

import com.example.clinic.domain.Clinic;
import com.example.clinic.dtos.ClinicDto;
import com.example.clinic.dtos.ServiceDto;
import com.example.clinic.exeption.ResourceNotCreatedException;
import com.example.clinic.exeption.ResourceNotFoundException;
import com.example.clinic.exeption.ResourceNotUpdateException;
import com.example.clinic.repositories.ClinicRepository;
import com.example.clinic.repositories.ServiceRepository;
import com.example.clinic.sevices.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;
    private final ServiceRepository serviceRepository;


    @Override
    public ClinicDto create(ClinicDto clinicDto) {
        Clinic newClinic = clinicRepository.create(ClinicDto.toDomain(clinicDto)).orElseThrow(() -> new ResourceNotCreatedException("The clinic was not created"));
        List<com.example.clinic.domain.Service> services = clinicDto.getServices()
                .stream()
                .map(serviceDto -> serviceRepository.create(ServiceDto.toDomain(serviceDto, newClinic.getId())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        return new ClinicDto(newClinic, services);
    }

    @Override
    public List<ClinicDto> getAll() {
        return clinicRepository.getAll()
                .stream()
                .map(clinic -> new ClinicDto(clinic, serviceRepository.getAllByClinicId(clinic.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public ClinicDto getById(long id) {
        return clinicRepository.getById(id)
                .map(clinic -> new ClinicDto(clinic, serviceRepository.getAllByClinicId(id)))
                .orElseThrow(() -> new ResourceNotFoundException("Clinic with id = " + id + " is not fond"));
    }

    @Override
    public ClinicDto update(ClinicDto clinicDto) {
        Clinic updatedClinic = clinicRepository.update(ClinicDto.toDomain(clinicDto))
                .orElseThrow(() -> new ResourceNotUpdateException("Clinic with id = " + clinicDto.getId() + " was not updated"));
        List<Long> idsServices = clinicDto.getServices().stream().map(ServiceDto::getId).collect(Collectors.toList());
        serviceRepository.getAllByClinicId(clinicDto.getId())
                .forEach(service -> {
                    if (idsServices.contains(service.getId())) serviceRepository.update(service);
                    else serviceRepository.delete(service.getId());
                });

        return ClinicDto.toDto(updatedClinic, serviceRepository.getAllByClinicId(updatedClinic.getId()));

    }

    @Override
    public void delete(long id) {
        serviceRepository.deleteAllByClinicId(id);
        clinicRepository.getById(id);
    }

    @Override
    public List<ClinicDto> getAllByText(String searchText, Integer pageNumber, Integer pageSize) {
        return clinicRepository.getAllByText(searchText, pageNumber, pageSize)
                .stream()
                .map(clinic -> new ClinicDto(clinic, serviceRepository.getAllByClinicId(clinic.getId())))
                .collect(Collectors.toList());
    }
}
