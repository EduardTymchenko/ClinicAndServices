package com.example.clinic.controllers;

import com.example.clinic.dtos.ServiceDto;
import com.example.clinic.sevices.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceDto create(
            @RequestBody ServiceDto service,
            @RequestParam Long idClinic) {
        return serviceService.create(service, idClinic);
    }

    @GetMapping
    public List<ServiceDto> getAll() {
        return serviceService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ServiceDto getById(@PathVariable Long id) {
        return serviceService.getById(id);
    }

    @PutMapping(value = "/{id}")
    public ServiceDto update(
            @PathVariable Long id,
            @RequestParam Long idClinic,
            @RequestBody ServiceDto service) {
        service.setId(id);
        return serviceService.update(service, idClinic);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        serviceService.delete(id);
    }

}
