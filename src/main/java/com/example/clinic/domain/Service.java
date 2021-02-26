package com.example.clinic.domain;


public class Service {
    private long id;
    private String name;
    private float fee;
    private int percentInsurance;
    private ThreadLocal startTime;
    private ThreadLocal endTime;
    private int daysOfWeek;

    public Service() {
    }

    public Service(long id, String name, float fee, int percentInsurance, ThreadLocal startTime, ThreadLocal endTime, int daysOfWeek) {
        this.id = id;
        this.name = name;
        this.fee = fee;
        this.percentInsurance = percentInsurance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.daysOfWeek = daysOfWeek;
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

    public int getPercentInsurance() {
        return percentInsurance;
    }

    public void setPercentInsurance(int percentInsurance) {
        this.percentInsurance = percentInsurance;
    }

    public ThreadLocal getStartTime() {
        return startTime;
    }

    public void setStartTime(ThreadLocal startTime) {
        this.startTime = startTime;
    }

    public ThreadLocal getEndTime() {
        return endTime;
    }

    public void setEndTime(ThreadLocal endTime) {
        this.endTime = endTime;
    }

    public int getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(int daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
}
