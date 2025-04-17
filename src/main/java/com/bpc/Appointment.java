package com.bpc;

public class Appointment {
    private final String bookingId;
    private String status;
    private String patientId;
    private Schedule schedule;

    public Appointment(String patientId,Schedule schedule, int totalEverNumOfAppointments) {
        this.bookingId = "booking-"+(totalEverNumOfAppointments+1);
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

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getBookingId() {
        return bookingId;
    }
}
