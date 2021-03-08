package com.example.clinic.domain;


public class Clinic {
    private long id;
    private String name;
    private String location;
    private String phone;
    private TypeClinicEnum type;
    private boolean hasInsurance;
    private int doctors;

    public Clinic() {
    }

    public Clinic(String name, String location, String phone, TypeClinicEnum type, boolean hasInsurance, int doctors) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.type = type;
        this.hasInsurance = hasInsurance;
        this.doctors = doctors;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public TypeClinicEnum getType() {
        return type;
    }

    public void setType(TypeClinicEnum type) {
        this.type = type;
    }

    public boolean isHasInsurance() {
        return hasInsurance;
    }

    public void setHasInsurance(boolean hasInsurance) {
        this.hasInsurance = hasInsurance;
    }

    public int getDoctors() {
        return doctors;
    }

    public void setDoctors(int doctors) {
        this.doctors = doctors;
    }


    @Override
    public String toString() {
        return "Clinic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", hasInsurance=" + hasInsurance +
                ", numberDoctors=" + doctors +
                '}';
    }
}
