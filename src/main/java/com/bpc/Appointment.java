package com.bpc;

public class Appointment {
    public String status;;
    public Patient patient;
    public Schedule schedule;

    public Appointment(Patient patient,Schedule schedule) {
        this.schedule = schedule;
        this.patient = patient;
        this.status = "BOOKED";
    }
}
