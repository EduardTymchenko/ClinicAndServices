package com.example.clinic.dtos;

import com.example.clinic.domain.Clinic;
import com.example.clinic.domain.Service;
import com.example.clinic.domain.TypeClinicEnum;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClinicDto {

    private long id;
    private String name;
    private String location;
    private String phone;
    private TypeClinicEnum type;
    private boolean hasInsurance;
    private int doctors;
    private List<ServiceDto> services;

    public ClinicDto(Clinic clinic, List<Service> services) {
        this.id = clinic.getId();
        this.name = clinic.getName();
        this.location = clinic.getLocation();
        this.phone = clinic.getPhone();
        this.type = clinic.getType();
        this.hasInsurance = clinic.isHasInsurance();
        this.doctors = clinic.getDoctors();
        this.services = CollectionUtils.isEmpty(services) ?
                Collections.emptyList() : services.stream().map(ServiceDto::new).collect(Collectors.toList());
    }

}
