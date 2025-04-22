package com.bpc;

import java.util.ArrayList;

public class Validation {

    public boolean isFullNameValid(String fullName) {
        return fullName.length() > 1 && fullName.matches("[a-zA-Z ]+");
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("^(\\+\\d{1,4})?\\d{9,14}$");
    }

    public boolean isAddressValid(String address) {
        return address.length() > 2;
    }

    public boolean isPatientValid(ArrayList<Patient> patients, String patientId) {
        return patients.stream().anyMatch(p -> p.getId().equals(patientId));
    }

    public boolean appointmentIsBookedOrAttended(ArrayList<Appointment> appointments, String scheduleId) {
        return appointments.stream().anyMatch(a -> a.getSchedule().getScheduleId().equals(scheduleId) && (a.getStatus().equals("BOOKED") || a.getStatus().equals("ATTENDED")));
    }

    public boolean patientIsBookedAtDatetime(ArrayList<Appointment> appointments, String patientId, String dateTime) {
        return appointments.stream().anyMatch(a -> a.getPatientId().equals(patientId) && a.getSchedule().getDateTime().equals(dateTime) && (a.getStatus().equals("BOOKED") || a.getStatus().equals("ATTENDED")));
    }
}
