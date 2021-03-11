package com.example.clinic.dtos;

import com.example.clinic.domain.Service;

public class ServiceDto {
    private long id;
    private String name;
    private float fee;
    private int coverage;
    private String time;
    private long clinicId;

    public ServiceDto(Service service) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.coverage = coverage;
        this.time = time;
        this.clinicId = clinicId;
    }
}
