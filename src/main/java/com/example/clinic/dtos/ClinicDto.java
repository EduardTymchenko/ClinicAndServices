package com.example.clinic.dtos;

import com.example.clinic.domain.TypeClinicEnum;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;


public class ClinicDto {
    private long id;
    private String name;
    private String location;
    private String phone;
    private TypeClinicEnum type;
    private boolean hasInsurance;
    private int doctors;
    private List<ServiceDto> services;
}
