package com.example.clinic.sevices.impl;

import com.example.clinic.domain.Clinic;
import com.example.clinic.domain.Service;
import com.example.clinic.domain.TypeClinicEnum;
import com.example.clinic.dtos.ClinicDto;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
    void create_clinicDtoWithOutServices_shouldReturnNewClinicDtoWithEmptyServiceList() {
        ClinicDto clinicDto = ClinicDto.builder()
                .name("Clinic dto")
                .location("Location dto")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(78)
                .services(Collections.emptyList())
                .build();
        Clinic clinicFromDto = ClinicDto.toDomain(clinicDto);
        Clinic clinicReturnRep = Clinic.builder()
                .id(45L)
                .name("Clinic dto")
                .location("Location dto")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(78)
                .build();
        when(clinicRepository.create(clinicFromDto)).thenReturn(Optional.of(clinicReturnRep));
        ClinicDto createdClinic = clinicService.create(clinicDto);

        assertNotNull(createdClinic);
        assertTrue(createdClinic.getId() > 0);
        assertEquals(0, clinicDto.getServices().size());
        assertNotEquals(clinicDto, createdClinic);
        verify(serviceRepository, never()).create(any(Service.class));
        verify(clinicRepository).create(Clinic.builder()
                .name("Clinic dto")
                .location("Location dto")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(78)
                .build());
    }

    @Test
    void create_clinicDtoWithServices_shouldReturnNewClinicDtoWithServiceList() {
        ClinicDto clinicDto = ClinicDto.builder()
                .name("Clinic dto")
                .location("Location dto")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(78)
                .services(Arrays.asList(new ServiceDto(), new ServiceDto()))
                .build();
        int numberServices = clinicDto.getServices().size();
        Clinic clinicFromDto = ClinicDto.toDomain(clinicDto);
        Clinic clinicReturnRep = Clinic.builder()
                .id(45L)
                .name("Clinic dto")
                .location("Location dto")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(78)
                .build();
        when(clinicRepository.create(clinicFromDto)).thenReturn(Optional.of(clinicReturnRep));
        ClinicDto createdClinic = clinicService.create(clinicDto);

        assertNotNull(createdClinic);
        assertTrue(createdClinic.getId() > 0);
        assertTrue(clinicDto.getServices().size() > 0);
        assertNotEquals(clinicDto, createdClinic);
        verify(serviceRepository, times(numberServices)).create(any(Service.class));
        verify(clinicRepository).create(Clinic.builder()
                .name("Clinic dto")
                .location("Location dto")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(78)
                .build());
    }

    @Test
    void create_notConnectDataBase_shouldReturnResourceNotCreatedException() {
        ResourceNotCreatedException ex =
                assertThrows(ResourceNotCreatedException.class, () -> clinicService.create(new ClinicDto()));

        assertEquals("The clinic was not created", ex.getMessage());

        verify(serviceRepository, never()).create(any(Service.class));
    }

    @Test
    void getAll_ClinicsPresent_shouldNotEmptyList() {
        when(clinicRepository.getAll()).thenReturn(Arrays.asList(new Clinic(), new Clinic()));

        List<ClinicDto> clinics = clinicService.getAll();
        int numberClinics = clinics.size();

        assertNotNull(clinics);
        assertTrue(clinics.size() > 0);
        verify(serviceRepository, times(numberClinics)).getAllByClinicId(anyLong());
    }

    @Test
    void getAll_ClinicsNotPresent_shouldEmptyList() {
        when(clinicRepository.getAll()).thenReturn(null);

        List<ClinicDto> clinics = clinicService.getAll();
        assertNotNull(clinics);
        assertTrue(clinics.isEmpty());
        verify(serviceRepository, times(0)).getAllByClinicId(anyLong());
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
    void update_existedClinicWithAllNewField_shouldReturnNewClinicDto() {
        ClinicDto newClinicDto = ClinicDto.builder()
                .id(44)
                .name("Clinic new")
                .location("Location new")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(22)
                .services(Arrays.asList(new ServiceDto(), new ServiceDto()))
                .build();
        Clinic newClinic = ClinicDto.toDomain(newClinicDto);
        Clinic updatedClinic = Clinic.builder()
                .id(44)
                .name("Clinic new")
                .location("Location new")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(22)
                .build();
        when(clinicRepository.update(newClinic)).thenReturn(Optional.of(updatedClinic));
        when(serviceRepository.getAllByClinicId(newClinicDto.getId())).thenReturn(Arrays.asList(new Service(), new Service()));

        ClinicDto updatedClinicDto = clinicService.update(newClinicDto);

        assertNotNull(updatedClinicDto);
        assertEquals(newClinicDto, updatedClinicDto);

        verify(clinicRepository).update(newClinic);
        verify(serviceRepository).getAllByClinicId(newClinicDto.getId());
        verify(serviceRepository, never()).update(any(Service.class));
        verify(serviceRepository, times(newClinicDto.getServices().size())).create(any(Service.class));
        verify(serviceRepository, times(2)).delete(anyLong());

    }

    @Test
    void update_existedClinicWithChangedService_shouldReturnNewClinicDto() {
        Service serviceNotChanged = Service.builder().id(22).clinicId(44L).build();
        ClinicDto newClinicDto = ClinicDto.builder()
                .id(44)
                .name("Clinic new")
                .location("Location new")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(22)
                .services(Arrays.asList(new ServiceDto(), new ServiceDto(), ServiceDto.toDto(serviceNotChanged)))
                .build();
        Clinic newClinic = ClinicDto.toDomain(newClinicDto);
        Clinic updatedClinic = Clinic.builder()
                .id(44)
                .name("Clinic new")
                .location("Location new")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(22)
                .build();
        when(clinicRepository.update(newClinic)).thenReturn(Optional.of(updatedClinic));
        when(serviceRepository.getAllByClinicId(newClinicDto.getId())).thenReturn(Arrays.asList(serviceNotChanged, new Service()));

        ClinicDto updatedClinicDto = clinicService.update(newClinicDto);

        assertNotNull(updatedClinicDto);
        assertEquals(newClinicDto, updatedClinicDto);

        verify(clinicRepository).update(newClinic);
        verify(serviceRepository).getAllByClinicId(newClinicDto.getId());
        verify(serviceRepository, times(1)).update(serviceNotChanged);
        verify(serviceRepository, times(newClinicDto.getServices().size() - 1)).create(any(Service.class));
        verify(serviceRepository, times(1)).delete(anyLong());
    }

    @Test
    void update_existedClinicWithServiceIsEmpty_shouldReturnNewClinicDto() {
        ClinicDto newClinicDto = ClinicDto.builder()
                .id(44)
                .name("Clinic new")
                .location("Location new")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(22)
                .services(Collections.emptyList())
                .build();
        Clinic newClinic = ClinicDto.toDomain(newClinicDto);
        Clinic updatedClinic = Clinic.builder()
                .id(44)
                .name("Clinic new")
                .location("Location new")
                .phone("0-800-123456")
                .type(TypeClinicEnum.PRIVATE)
                .hasInsurance(true)
                .doctors(22)
                .build();
        when(clinicRepository.update(newClinic)).thenReturn(Optional.of(updatedClinic));
        when(serviceRepository.getAllByClinicId(newClinicDto.getId())).thenReturn(Arrays.asList(new Service(), new Service()));

        ClinicDto updatedClinicDto = clinicService.update(newClinicDto);

        assertNotNull(updatedClinicDto);
        assertEquals(newClinicDto, updatedClinicDto);

        verify(clinicRepository).update(newClinic);
        verify(serviceRepository).getAllByClinicId(newClinicDto.getId());
        verify(serviceRepository, never()).update(any(Service.class));
        verify(serviceRepository, never()).create(any(Service.class));
        verify(serviceRepository).deleteAllByClinicId(anyLong());
    }

    @Test
    void update_notExistedClinic_shouldReturnThrowException() {
        ClinicDto notExistClinicDto = ClinicDto.builder()
                .id(34)
                .build();
        when(clinicRepository.update(any(Clinic.class))).thenReturn(Optional.empty());

        ResourceNotUpdateException ex =
                assertThrows(ResourceNotUpdateException.class, () -> clinicService.update(notExistClinicDto));

        assertEquals("Clinic with id = " + 34 + " was not updated", ex.getMessage());

        verify(serviceRepository, never()).getAllByClinicId(anyLong());
        verify(serviceRepository, never()).deleteAllByClinicId(anyLong());
        verify(serviceRepository, never()).delete(anyLong());
        verify(serviceRepository, never()).update(any(Service.class));
        verify(serviceRepository, never()).create(any(Service.class));

    }

    @Test
    void delete_existedClinic_shouldCallRepositories() {
        long existedId = 5L;
        when(clinicRepository.getById(existedId)).thenReturn(Optional.of(new Clinic()));

        clinicService.delete(existedId);

        verify(clinicRepository).delete(existedId);
        verify(serviceRepository).deleteAllByClinicId(existedId);
    }

    @Test
    void delete_notExistedClinic_shouldNonCallRepositoriesDeleteMethodsAndThrowException() {
        long notExistedId = 5L;
        when(clinicRepository.getById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(ResourceNotFoundException.class, () -> clinicService.getById(notExistedId));

        assertEquals("Clinic with id = " + notExistedId + " is not fond", ex.getMessage());

        verify(clinicRepository, never()).delete(notExistedId);
        verify(serviceRepository, never()).deleteAllByClinicId(notExistedId);
    }

    @Test
    void getAllByText_WithResult_shouldNotEmptyListAndCallServiceRep() {
        when(clinicRepository.getAllByText(anyString(), anyInt(), anyInt())).thenReturn(Arrays.asList(new Clinic(), new Clinic()));
        List<ClinicDto> result =  clinicService.getAllByText(anyString(), anyInt(), anyInt());
        assertNotNull(result);
        assertTrue(result.size() > 0);
        verify(serviceRepository, times(2)).getAllByClinicId(anyLong());
    }

    @Test
    void getAllByText_WithoutResult_shouldEmptyListAndNotCallServiceRep() {
        when(clinicRepository.getAllByText(anyString(), anyInt(), anyInt())).thenReturn(Collections.emptyList());
        List<ClinicDto> result =  clinicService.getAllByText(anyString(), anyInt(), anyInt());
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(serviceRepository, never()).getAllByClinicId(anyLong());
    }
}