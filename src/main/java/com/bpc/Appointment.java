package com.bpc;

public class Appointment {
    private final String bookingId;
    private final String patientId;
    private final Schedule schedule;
    private String status;

    public Appointment(String patientId, Schedule schedule, int totalEverNumOfAppointments) {
        this.bookingId = "booking-" + (totalEverNumOfAppointments + 1);
        this.schedule = schedule;
        this.patientId = patientId;
        this.status = "BOOKED";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPatientId() {
        return patientId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getBookingId() {
        return bookingId;
    }
}
