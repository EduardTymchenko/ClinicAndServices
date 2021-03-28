package com.example.clinic.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Table(name = "clinics")
@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String location;
    private String phone;
    private TypeClinicEnum type;
    private boolean hasInsurance;
    private int doctors;
    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY)
    private List<Service> services;


    public Clinic(String name, String location, String phone, TypeClinicEnum type, boolean hasInsurance, int doctors) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.type = type;
        this.hasInsurance = hasInsurance;
        this.doctors = doctors;
    }

    public Clinic(long id, String name, String location, String phone, TypeClinicEnum type, boolean hasInsurance, int doctors) {
    }
}
