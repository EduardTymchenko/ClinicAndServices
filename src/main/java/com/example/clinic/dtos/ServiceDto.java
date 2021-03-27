package com.example.clinic.dtos;

import com.example.clinic.domain.Service;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
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

    public static Service toDomain(ServiceDto serviceDto,  long clinicId) {
        return new Service(
                serviceDto.getId(),
                serviceDto.getName(),
                serviceDto.getFee(),
                serviceDto.getCoverage(),
                serviceDto.getTime(),
                clinicId);

    }

    public static ServiceDto toDto(Service service) {
        return new ServiceDto(service);
    }
}
