package com.example.clinic.sevices.impl;

import com.example.clinic.domain.Clinic;
import com.example.clinic.dtos.ClinicDto;
import com.example.clinic.repositories.ClinicRepository;
import com.example.clinic.repositories.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;
class ClinicServiceImplTest {

    private ClinicServiceImpl clinicService;

    @Mock
    private ClinicRepository clinicRepository;
    @Mock
    private ServiceRepository serviceRepository;

    @BeforeEach
    void setUP() {
        MockitoAnnotations.openMocks(this);
        clinicService = new ClinicServiceImpl(clinicRepository, serviceRepository);
    }

    @Test
    void create() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
        long existedId = 2L;
        when(clinicRepository.getById(existedId))
                .thenReturn(Optional.of(Clinic.builder()
                        .id(existedId)
                        .build()));

        ClinicDto clinicDto = clinicService.getById(existedId);
        assertNotNull(clinicDto);
        assertEquals(existedId, clinicDto.getId());
        assertEquals(new ClinicDto(), clinicDto);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllByText() {
    }
}