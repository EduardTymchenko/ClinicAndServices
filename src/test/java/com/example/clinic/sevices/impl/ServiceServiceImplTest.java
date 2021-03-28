package com.example.clinic.sevices.impl;

import com.example.clinic.domain.Clinic;
import com.example.clinic.domain.Service;
import com.example.clinic.dtos.ServiceDto;
import com.example.clinic.exeption.ResourceNotCreatedException;
import com.example.clinic.exeption.ResourceNotFoundException;
import com.example.clinic.exeption.ResourceNotUpdateException;
import com.example.clinic.repositories.ClinicRepository;
import com.example.clinic.repositories.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceServiceImplTest {

    @InjectMocks
    private ServiceServiceImpl serviceService;

    @Mock
    private ClinicRepository clinicRepository;
    @Mock
    private ServiceRepository serviceRepository;

    @BeforeEach
    void setUP() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_existIdClinic_shouldReturnNewServiceDto() {
        long excitedIdClinic = 3L;
        ServiceDto newServiceDto = ServiceDto.builder()
                .name("new Service")
                .fee(100)
                .coverage(20)
                .time("10:00-17:00")
                .build();
        Service newService = ServiceDto.toDomain(newServiceDto, excitedIdClinic);
        Service serviceRep = Service.builder()
                .id(45L)
                .name("new Service")
                .fee(100)
                .coverage(20)
                .time("10:00-17:00")
                .build();

        when(serviceRepository.create(newService)).thenReturn(Optional.of(serviceRep));
        when(clinicRepository.getById(excitedIdClinic)).thenReturn(Optional.of(new Clinic()));

        ServiceDto createdService = serviceService.create(newServiceDto, excitedIdClinic);

        assertNotNull(createdService);
        assertTrue(createdService.getId() > 0);

        verify(clinicRepository).getById(excitedIdClinic);
        verify(serviceRepository).create(newService);
    }

    @Test
    void create_notExistIdClinic_shouldReturnResourceNotCreatedException() {
        long notExcitedIdClinic = 3000L;
        ServiceDto newServiceDto = ServiceDto.builder()
                .name("new Service")
                .fee(100)
                .coverage(20)
                .time("10:00-17:00")
                .build();

        when(clinicRepository.getById(notExcitedIdClinic)).thenReturn(Optional.empty());

        ResourceNotCreatedException ex =
                assertThrows(ResourceNotCreatedException.class, () -> serviceService.create(newServiceDto, notExcitedIdClinic));

        assertEquals("Service was not created. Clinic with id = " + notExcitedIdClinic + " is not exists", ex.getMessage());

        verify(clinicRepository).getById(notExcitedIdClinic);
        verify(serviceRepository, never()).create(any(Service.class));
    }

    @Test
    void getAll_ServicePresent_shouldNotEmptyList() {
        when(serviceRepository.getAll()).thenReturn(Arrays.asList(new Service(), new Service()));

        List<ServiceDto> serviceDtoList = serviceService.getAll();

        assertNotNull(serviceDtoList);
        assertTrue(serviceDtoList.size() > 0);
        verify(serviceRepository).getAll();
    }

    @Test
    void getAll_ClinicsNotPresent_shouldEmptyList() {
        when(clinicRepository.getAll()).thenReturn(null);

        List<ServiceDto> serviceDtoList = serviceService.getAll();

        assertNotNull(serviceDtoList);
        assertTrue(serviceDtoList.isEmpty());
        verify(serviceRepository).getAll();
    }

    @Test
    void getById_existedService_shouldReturnServiceDto() {
        long existId = 10L;
        when(serviceRepository.getById(existId)).thenReturn(Optional.of(Service.builder()
                .id(existId)
                .name("service")
                .coverage(20)
                .fee(45)
                .time("10:00-13:00")
                .clinicId(19L)
                .build()));
        ServiceDto existedServiceDto = serviceService.getById(existId);
        assertNotNull(existedServiceDto);
        assertEquals(existId, existedServiceDto.getId());
        assertEquals("service", existedServiceDto.getName());
        assertEquals(20, existedServiceDto.getCoverage());
        assertEquals(45, existedServiceDto.getFee());
        assertEquals("10:00-13:00", existedServiceDto.getTime());
        verify(serviceRepository).getById(existId);
    }

    @Test
    void getById_notExistedService_shouldReturnResourceNotFoundException() {

        when(serviceRepository.getById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class, () -> serviceService.getById(238L));

        assertEquals("Service with id = " + 238 + " is not found", ex.getMessage());
        verify(serviceRepository).getById(238L);
    }

    @Test
    void update_existedClinicIdAndService_shouldReturnNewServiceDto() {
        long existedClinicId = 10L;
        ServiceDto newServiceDto = ServiceDto.builder()
                .id(20L)
                .name("service dto")
                .coverage(20)
                .fee(45)
                .time("10:00-13:00")
                .build();
        Service newService = ServiceDto.toDomain(newServiceDto, existedClinicId);

        when(clinicRepository.getById(existedClinicId)).thenReturn(Optional.of(new Clinic()));
        when(serviceRepository.update(newService)).thenReturn(Optional.of(newService));

        ServiceDto updatedServiceDto = serviceService.update(newServiceDto, existedClinicId);

        assertNotNull(updatedServiceDto);
        assertEquals(newServiceDto, updatedServiceDto);
        verify(clinicRepository).getById(existedClinicId);
        verify(serviceRepository).update(newService);
    }

    @Test
    void update_existedClinicIdNotExistedService_shouldReturnResourceNotUpdateException() {
        long existedClinicId = 10L;
        ServiceDto newServiceDto = ServiceDto.builder()
                .id(20L)
                .name("service dto")
                .coverage(20)
                .fee(45)
                .time("10:00-13:00")
                .build();

        when(clinicRepository.getById(existedClinicId)).thenReturn(Optional.of(new Clinic()));
        when(serviceRepository.update(any(Service.class))).thenReturn(Optional.empty());

        ResourceNotUpdateException ex =
                assertThrows(ResourceNotUpdateException.class, () ->  serviceService.update(newServiceDto, existedClinicId));

        assertEquals("Service was not update for Clinic id = " + existedClinicId, ex.getMessage());
        verify(clinicRepository).getById(existedClinicId);
        verify(serviceRepository).update(any(Service.class));
    }

    @Test
    void update_notExistedClinicIdExistedService_shouldReturnResourceNotUpdateException() {
        long notExistedClinicId = 104L;
        ServiceDto newServiceDto = ServiceDto.builder()
                .id(20L)
                .name("service dto")
                .coverage(20)
                .fee(45)
                .time("10:00-13:00")
                .build();

        when(clinicRepository.getById(notExistedClinicId)).thenReturn(Optional.empty());
        when(serviceRepository.update(any(Service.class))).thenReturn(Optional.empty());

        ResourceNotUpdateException ex =
                assertThrows(ResourceNotUpdateException.class, () ->  serviceService.update(newServiceDto, notExistedClinicId));

        assertEquals("Service was not update. Clinic with id = " +
                notExistedClinicId + " is not found", ex.getMessage());
        verify(clinicRepository).getById(notExistedClinicId);
        verify(serviceRepository, never()).update(any(Service.class));
    }

    @Test
    void delete() {
        serviceService.delete(anyLong());
        verify(serviceRepository).delete(anyLong());
    }

    @Test
    void getAllByText() {
    }

    @Test
    void getAllByText_WithResult_shouldNotEmptyList() {
        when(serviceRepository.getAllByText(anyString(), anyInt(), anyInt())).thenReturn(Arrays.asList(new Service(), new Service()));

        List<ServiceDto> result =  serviceService.getAllByText(anyString(), anyInt(), anyInt());

        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test
    void getAllByText_WithoutResult_shouldEmptyListAndNotCallServiceRep() {
        when(serviceRepository.getAllByText(anyString(), anyInt(), anyInt())).thenReturn(Collections.emptyList());

        List<ServiceDto> result = serviceService.getAllByText(anyString(), anyInt(), anyInt());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}