package com.bpc;

public class Appointment {
    public int hour, day, week, room;
    public String status;;
    public Patient patient;
    public Physiotherapist physiotherapist;

    public Appointment(int hour, int day, int week, int room, Patient patient, Physiotherapist physiotherapist) {
        this.hour = hour;
        this.day = day;
        this.week = week;
        this.room = room;
        this.patient = patient;
        this.physiotherapist = physiotherapist;
        this.status = "BOOKED";
    }
}
