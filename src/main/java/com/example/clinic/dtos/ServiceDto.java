package com.example.clinic.dtos;

import com.example.clinic.domain.Service;
import lombok.ToString;

@ToString
public class ServiceDto {
    private long id;
    private String name;
    private float fee;
    private int coverage;
    private String time;


    public ServiceDto(Service service) {
        this.id = service.getId();
        this.name = service.getName();
        this.fee = service.getFee();
        this.coverage = service.getCoverage();
        this.time = service.getTime();
    }
}
