package com.example.clinic.domain;


public class Service {
    private long id;
    private String name;
    private float fee;
    private int percentInsurance;
    private String time;


    public Service() {
    }

    public Service(long id, String name, float fee, int percentInsurance, String time) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.percentInsurance = percentInsurance;
        this.time = time;
    }

    public long getId() {
        return id;
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

    public int getPercentInsurance() {
        return percentInsurance;
    }

    public void setPercentInsurance(int percentInsurance) {
        this.percentInsurance = percentInsurance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
