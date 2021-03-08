package com.example.clinic.domain;


public class Service {
    private long id;
    private String name;
    private float fee;
    private int coverage;
    private String time;
    private long clinicId;


    public Service() {
    }

    public Service(String name, float fee, int percentInsurance, String time, long clinicId) {
        this.name = name;
        this.fee = fee;
        this.coverage = percentInsurance;
        this.time = time;
        this.clinicId = clinicId;
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

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getCoverage() {
        return coverage;
    }

    public void setCoverage(int coverage) {
        this.coverage = coverage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getClinicId() {
        return clinicId;
    }

    public void setClinicId(long clinicId) {
        this.clinicId = clinicId;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fee=" + fee +
                ", percentInsurance=" + coverage +
                ", time='" + time + '\'' +
                ", clinicId=" + clinicId +
                '}';
    }
}
