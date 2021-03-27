package com.example.clinic.sevices.impl;

import com.example.clinic.domain.Clinic;
import com.example.clinic.domain.Service;
import com.example.clinic.domain.TypeClinicEnum;
import com.example.clinic.dtos.ClinicDto;
import com.example.clinic.exeption.ResourceNotFoundException;
import com.example.clinic.exeption.ResourceNotUpdateException;
import com.example.clinic.repositories.ClinicRepository;
import com.example.clinic.repositories.ServiceRepository;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClinicServiceImplTest {

    @InjectMocks
    private ClinicServiceImpl clinicService;

    @Mock
    private ClinicRepository clinicRepository;
    @Mock
    private ServiceRepository serviceRepository;

    @BeforeEach
    void setUP() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById_existedClinicNotExistedServices_shouldReturnClinicWithoutServices() {
        long existedId = 2L;
        String existedName = "Clinic One";
        String existedLocation = "City";
        String existedPhone = "050-111-22-22";
        TypeClinicEnum existedType = TypeClinicEnum.PRIVATE;
        boolean hasInsurance = true;
        int existedDoctors = 50;

        Clinic clinicFromRepository = Clinic.builder()
                .id(existedId)
                .name(existedName)
                .location(existedLocation)
                .phone(existedPhone)
                .type(existedType)
                .hasInsurance(hasInsurance)
                .doctors(existedDoctors)
                .build();
        when(clinicRepository.getById(existedId))
                .thenReturn(Optional.of(clinicFromRepository));

        ClinicDto clinicDto = clinicService.getById(existedId);
        verify(clinicRepository).getById(existedId);
        verify(serviceRepository).getAllByClinicId(existedId);

        assertNotNull(clinicDto);

        assertEquals(existedId, clinicDto.getId());
        assertEquals(existedName, clinicDto.getName());
        assertEquals(existedLocation, clinicDto.getLocation());
        assertEquals(existedPhone, clinicDto.getPhone());
        assertEquals(existedType, clinicDto.getType());
        assertEquals(hasInsurance, clinicDto.isHasInsurance());
        assertEquals(existedDoctors, clinicDto.getDoctors());

        assertEquals(new ClinicDto(clinicFromRepository, null), clinicDto);
        assertEquals(new ClinicDto(clinicFromRepository, Collections.emptyList()), clinicDto);

        assertNotNull(clinicDto.getServices());
        assertEquals(0, clinicDto.getServices().size());
    }

    @Test
    void getById_existedClinicExistedServices_shouldReturnClinicWithServices() {
        long existedId = 2L;
        String existedName = "Clinic One";
        String existedLocation = "City";
        String existedPhone = "050-111-22-22";
        TypeClinicEnum existedType = TypeClinicEnum.PRIVATE;
        boolean hasInsurance = true;
        int existedDoctors = 50;

        List<Service> existedServices = Arrays.asList(new Service(), new Service());

        Clinic clinicFromRepository = Clinic.builder()
                .id(existedId)
                .name(existedName)
                .location(existedLocation)
                .phone(existedPhone)
                .type(existedType)
                .hasInsurance(hasInsurance)
                .doctors(existedDoctors)
                .build();
        when(clinicRepository.getById(existedId))
                .thenReturn(Optional.of(clinicFromRepository));
        when(serviceRepository.getAllByClinicId(existedId))
                .thenReturn(existedServices);

        ClinicDto clinicDto = clinicService.getById(existedId);
        verify(clinicRepository).getById(existedId);
        verify(serviceRepository).getAllByClinicId(existedId);

        assertNotNull(clinicDto);

        assertEquals(existedId, clinicDto.getId());
        assertEquals(existedName, clinicDto.getName());
        assertEquals(existedLocation, clinicDto.getLocation());
        assertEquals(existedPhone, clinicDto.getPhone());
        assertEquals(existedType, clinicDto.getType());
        assertEquals(hasInsurance, clinicDto.isHasInsurance());
        assertEquals(existedDoctors, clinicDto.getDoctors());

        assertEquals(new ClinicDto(clinicFromRepository, existedServices), clinicDto);

        assertNotNull(clinicDto.getServices());
        assertEquals(existedServices.size(), clinicDto.getServices().size());
    }

    @Test
    void getById_notExistedIDClinics_shouldReturnResourceNotFoundException() {
        long notExistedId = 20000L;

        when(clinicRepository.getById(anyLong()))
                .thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class, () -> clinicService.getById(notExistedId));

        assertEquals("Clinic with id = " + notExistedId + " is not fond", ex.getMessage());

        verify(serviceRepository, never()).getAllByClinicId(anyLong());
    }

    @Test
    void update() {
    }

    @Test
    void delete_existedClinic_shouldCallRepositories() {
        long existedId = 5L;
        clinicService.delete(existedId);

        verify(clinicRepository).delete(existedId);
        verify(serviceRepository).deleteAllByClinicId(existedId);
    }

    @Test
    void getAllByText() {
    }
}