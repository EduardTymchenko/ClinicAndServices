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
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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
        Clinic newClinic = clinicRepository.create(ClinicDto.toDomain(clinicDto))
                .orElseThrow(() -> new ResourceNotCreatedException("The clinic was not created"));
        List<ServiceDto> serviceDtoList = clinicDto.getServices();
        List<com.example.clinic.domain.Service> services = null;
        if (serviceDtoList != null && !serviceDtoList.isEmpty()) {
            services = serviceDtoList.stream()
                    .map(serviceDto -> serviceRepository.create(ServiceDto.toDomain(serviceDto, newClinic.getId())))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }

        return new ClinicDto(newClinic, services);
    }

    @Override
    public List<ClinicDto> getAll() {
        List<Clinic> clinics = clinicRepository.getAll();
        if (clinics == null || clinics.isEmpty()) return Collections.emptyList();
        return clinics
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

        List<com.example.clinic.domain.Service> newServices = clinicDto.getServices()
                .stream()
                .map(serviceDto -> ServiceDto.toDomain(serviceDto, updatedClinic.getId()))
                .collect(Collectors.toList());
        List<com.example.clinic.domain.Service> oldServices = serviceRepository.getAllByClinicId(clinicDto.getId());
        if (CollectionUtils.isEmpty(newServices)) {
            if (!CollectionUtils.isEmpty(oldServices)) serviceRepository.deleteAllByClinicId(updatedClinic.getId());
            return ClinicDto.toDto(updatedClinic, newServices);
        }

        if (newServices.size() == oldServices.size() && newServices.containsAll(oldServices)) {
            return ClinicDto.toDto(updatedClinic, newServices);
        }

        newServices.forEach(newService -> {
            if (oldServices.contains(newService)) serviceRepository.update(newService);
            else serviceRepository.create(newService);
        });

        oldServices.forEach(oldService -> {
            if (!newServices.contains(oldService)) serviceRepository.delete(oldService.getId());
        });


        return ClinicDto.toDto(updatedClinic, newServices);

    }

    @Override
    public void delete(long id) {
        clinicRepository.getById(id).map(clinic -> {
            serviceRepository.deleteAllByClinicId(id);
            clinicRepository.delete(id);
            return clinic;
        }).orElseThrow(() -> new ResourceNotFoundException("Clinic with id = " + id + " is not fond"));
    }

    @Override
    public List<ClinicDto> getAllByText(String searchText, Integer pageNumber, Integer pageSize) {
        return clinicRepository.getAllByText(searchText, pageNumber, pageSize)
                .stream()
                .map(clinic -> new ClinicDto(clinic, serviceRepository.getAllByClinicId(clinic.getId())))
                .collect(Collectors.toList());
    }
}
