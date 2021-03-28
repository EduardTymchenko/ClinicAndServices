package com.example.clinic.dtos;

import com.example.clinic.domain.Service;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
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

    public static Service toDomain(ServiceDto serviceDto) {
        return new Service(
                serviceDto.getId(),
                serviceDto.getName(),
                serviceDto.getFee(),
                serviceDto.getCoverage(),
                serviceDto.getTime());

    }

    public static ServiceDto toDto(Service service) {
        return new ServiceDto(service);
    }
}
