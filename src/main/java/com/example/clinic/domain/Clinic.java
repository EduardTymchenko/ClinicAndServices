package com.example.clinic.domain;

import java.util.List;

public class Clinic {
    private long id;
    private String name;
    private String location;
    private String phone;
    private boolean isPublic;
    private boolean isInsurance;
    private int numberDoctors;
    private List<Service> serviceList;

    public Clinic() {
    }

    public Clinic(String name, String location, String phone, boolean isPublic, boolean isInsurance, int numberDoctors, List<Service> serviceList) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.isPublic = isPublic;
        this.isInsurance = isInsurance;
        this.numberDoctors = numberDoctors;
        this.serviceList = serviceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isInsurance() {
        return isInsurance;
    }

    public void setInsurance(boolean insurance) {
        isInsurance = insurance;
    }

    public int getNumberDoctors() {
        return numberDoctors;
    }

    public void setNumberDoctors(int numberDoctors) {
        this.numberDoctors = numberDoctors;
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
}
