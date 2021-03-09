package com.example.clinic.listeneres;

import com.example.clinic.domain.Clinic;
import com.example.clinic.domain.Service;
import com.example.clinic.domain.TypeClinicEnum;
import com.example.clinic.repositories.ClinicRepository;
import com.example.clinic.repositories.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class ApplicationReadyListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyListener.class);

    private final ClinicRepository clinicRepository;
    private final ServiceRepository serviceRepository;

    public ApplicationReadyListener(ClinicRepository clinicRepository, ServiceRepository serviceRepository) {
        this.clinicRepository = clinicRepository;
        this.serviceRepository = serviceRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReadyHandler() {

        for (int i = 0; i < 10; i++) {
            createClinics(3);
        }

        LOGGER.info("***** Select All clinics *****");
        for (Clinic clinic:clinicRepository.getAll()){
            LOGGER.info("{}",clinic);
        }
        LOGGER.info("Total records: {}",clinicRepository.getNumberOfClinics());

        LOGGER.info("***** Update  clinic *****");
        Clinic clinicForUpdate = clinicRepository.getAll().get(0);
        LOGGER.info("Clinic before update = {}",clinicForUpdate);
        clinicForUpdate.setName("Update name");
        LOGGER.info("Clinic after update = {}",clinicRepository.update(clinicForUpdate));

        LOGGER.info("Set of clinics name = {}",clinicRepository.getNamesClinicInsurance(true));
// Service
        for (int i = 0; i < 10; i++) {
            createServices(4);
        }

        LOGGER.info("***** Select All services *****");
        for (Service service:serviceRepository.getAll()){
            LOGGER.info("{}",service);
        }
        LOGGER.info("Total records: {}",serviceRepository.getNumberOfServices());

        LOGGER.info("***** Update  services *****");
        Service serviceForUpdate = serviceRepository.getAll().get(0);
        LOGGER.info("Service before update = {}",serviceForUpdate);
        serviceForUpdate.setName("Update Service");
        LOGGER.info("Service after update = {}",serviceRepository.update(serviceForUpdate));

        LOGGER.info("Set of services name = {}",serviceRepository.getNamesByFee(20,80));

        LOGGER.info("***** Delete all  services *****");
        List<Long> idsService = serviceRepository.getAll().stream().map(Service::getId).collect(Collectors.toList());
        idsService.stream().peek(serviceRepository::delete).collect(Collectors.toList());
        LOGGER.info("Total records: {}",serviceRepository.getNumberOfServices());

        LOGGER.info("***** Delete all  clinic *****");
        clinicRepository.getAll().stream().peek((clinic -> clinicRepository.delete(clinic.getId()))).collect(Collectors.toList());
        LOGGER.info("Total records: {}",serviceRepository.getNumberOfServices());

    }


    private void createClinics(int number){
        LOGGER.info("***** Start create clinics *****");
        for (Clinic clinic: createListClinic(number)) {
            Optional<Clinic> clinicAdded = clinicRepository.create(clinic);
            String inf;
            if(clinicAdded.isPresent()) {
                inf = clinicAdded.get().toString();
            } else inf = "no added";
            LOGGER.info("Clinic added = {}",inf);
        }
        LOGGER.info("***** End create clinics *****");

    }

    private void createServices(int number){
        LOGGER.info("***** Start create services *****");
        for (Service service: createListServices(number)) {
            Optional<Service> servicesAdded = serviceRepository.create(service);
            LOGGER.info("Service added = {}",servicesAdded);
        }
        LOGGER.info("***** End create services *****");
    }

    private List<Clinic> createListClinic(int number){
        List<Clinic> clinics = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            clinics.add( new Clinic("clinic " + i, "address " + i,"050-22-22-22" + i,
                    TypeClinicEnum.values()[random.nextBoolean() ? 1 : 0],random.nextBoolean(),random.nextInt(100)));
        }
        return clinics;
    }

    private List<Service> createListServices(int number){
        List<Service> services = new ArrayList<>();
        Random random = new Random();
        List<Long> idsClinic = clinicRepository.getAll().stream().map((Clinic::getId)).collect(Collectors.toList());
        for (int i = 0; i < number; i++) {
            services.add( new Service("Service " + i, random.nextFloat() * 100,random.nextInt(50),
                    random.nextInt(3) + "h" + random.nextInt(59) + "m",
                    idsClinic.get(random.nextInt(idsClinic.size() - 1))));
        }
        return services;
    }
}
