package com.example.clinic.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Table(name = "services")
@Entity
public class Service {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private float fee;
    private int coverage;
    private String time;
//    private long clinicId;
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    public Service(String name, float fee, int coverage, String time) {
        this.name = name;
        this.fee = fee;
        this.coverage = coverage;
        this.time = time;
    }

    public Service(long id, String name, float fee, int coverage, String time) {
    }
}