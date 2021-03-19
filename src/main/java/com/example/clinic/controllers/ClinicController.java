package com.example.clinic.controllers;

import com.example.clinic.dtos.ClinicDto;
import com.example.clinic.sevices.ClinicService;
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
@RequestMapping(value = "/v1/clinics")
@RequiredArgsConstructor
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping
    public List<ClinicDto> getAll() {
        return clinicService.getAll();
    }

    @GetMapping(value = "/{id}")
    public ClinicDto getOne(@PathVariable Long id) {
        return clinicService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClinicDto create(@RequestBody ClinicDto clinicDto) {
        return clinicService.create(clinicDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clinicService.delete(id);
    }

    @PutMapping(value = "/{id}")
    public ClinicDto update(
            @PathVariable Long id,
            @RequestBody ClinicDto clinicDto) {
        clinicDto.setId(id);
        return clinicService.update(clinicDto);
    }

    @GetMapping(value = "/search")
    public List<ClinicDto> search(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber) {
        return clinicService.getAllByText(searchText, pageNumber, pageSize);
    }

}
