package com.example.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Clinic {
    private long id;
    private String name;
    private String location;
    private String phone;
    private TypeClinicEnum type;
    private boolean hasInsurance;
    private int doctors;



//    public Clinic(String name, String location, String phone, TypeClinicEnum type, boolean hasInsurance, int doctors) {
//        this.name = name;
//        this.location = location;
//        this.phone = phone;
//        this.type = type;
//        this.hasInsurance = hasInsurance;
//        this.doctors = doctors;
//    }

}
