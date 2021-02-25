package com.example.clinic.domain;


public class Service {
    private String name;
    private float fee;
    private int percentInsurance;
    private TimeService timeService;

    public Service() {
    }

    public Service(String name, float fee, int percentInsurance, TimeService timeService) {
        this.name = name;
        this.fee = fee;
        this.percentInsurance = percentInsurance;
        this.timeService = timeService;
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

    public TimeService getTimeService() {
        return timeService;
    }

    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }
}
