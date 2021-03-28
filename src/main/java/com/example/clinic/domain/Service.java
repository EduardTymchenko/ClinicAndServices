package com.example.clinic.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Service {

    private long id;
    private String name;
    private float fee;
    private int coverage;
    private String time;
    private long clinicId;

}