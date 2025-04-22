package com.bpc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClinicDataTest {
    @Test
    void testAddPatient() {
        System.out.println("addPatient");
        ClinicData instance = new ClinicData();
        int numOfPatients = instance.getPatientsSize();
        instance.addPatient("Abedi Pele", "Some address", "+4490443837334");

        int expected = numOfPatients + 1; //expect total number of patients to increase by 1
        int result = instance.getPatientsSize();
        assertEquals(expected, result);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> instance.addPatient("A", "Some address", "+44904438373"));
        assertEquals("Full name is invalid", ex.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> instance.addPatient("Abedi Pele", "S", "+44904438373"));
        assertEquals("Address is invalid", ex2.getMessage());

        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> instance.addPatient("Abedi Pele", "Some address", "+449"));
        assertEquals("Phone number is invalid", ex3.getMessage());
    }

    @Test
    void testGetPatient() {
        System.out.println("getPatient");
        ClinicData instance = new ClinicData();

        Patient pat = instance.addPatient("Abedi Pele", "Some address", "+44904438373");
        Patient getPat = instance.getPatient(pat.getId());

        assertEquals(pat.getId(), getPat.getId());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> instance.getPatient("incorrectId"));
        assertEquals("Patient is invalid", ex.getMessage());
    }

    @Test
    void testRemovePatient() {
        System.out.println("removePatient");
        ClinicData instance = new ClinicData();
        int numOfPatients = instance.getPatientsSize();
        instance.bookAppointment("schedule-1", "patient-1");
        instance.removePatient("patient-1");

        int expected = numOfPatients - 1; //expect total number of patients to decrease by 1
        int result = instance.getPatientsSize();
        assertEquals(expected, result);

        //check if patients appointments remain
        ArrayList<Appointment> patientAppointments = instance.getPatientAppointments("patient-1");
        expected = 1;
        result = patientAppointments.size();
        assertEquals(expected, result);

        //check if all patients appointments are cancelled
        patientAppointments.forEach(a -> assertEquals("CANCELLED", a.getStatus()));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> instance.removePatient("incorrectId"));
        assertEquals("Patient is invalid", ex.getMessage());
    }

    @Test
    void testGetPatientsSize() {
        System.out.println("getPatientsSize");
        ClinicData instance = new ClinicData();
        int result = instance.getPatientsSize();
        int expected = 10; // 10 patients get loaded via ClinicData constructor initially
        assertEquals(expected, result); //expect the initial number of patients to be 10

        //test when another patient is added
        instance.addPatient("Abedi Pele", "Some address", "+44904438373");
        expected++; //Number of patients should increase by 1
        result = instance.getPatientsSize();
        assertEquals(expected, result);
    }

    @Test
    void testBookAppointment() {
        System.out.println("bookAppointment");
        ClinicData instance = new ClinicData();
        int totalNumOfAppointments = instance.getAppointmentsSize();

        instance.bookAppointment("schedule-1", "patient-1");

        int expected = totalNumOfAppointments + 1;
        int result = instance.getAppointmentsSize();
        assertEquals(expected, result); //passes of appointment got booked

        //Test when an invalid patient id is passed
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> instance.bookAppointment("schedule-1", "pat-1")); //pat-1 is not an available patient id
        assertEquals("Patient is invalid", ex.getMessage());

        //Test when an invalid schedule id is passed
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> instance.bookAppointment("schedule-100", "patient-1")); //schedule-100 is not an available schedule
        assertEquals("Schedule not found", ex2.getMessage());

        //test when the schedule has already been booked or attended
        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> instance.bookAppointment("schedule-1", "patient-2")); //this was already booked by patient-1 in our first edge-case
        assertEquals("Appointment already booked or attended", ex3.getMessage());

        //test when patient tries to book the same appointment twice
        IllegalArgumentException ex4 = assertThrows(IllegalArgumentException.class, () -> instance.bookAppointment("schedule-1", "patient-1"));
        assertEquals("Appointment already booked or attended", ex4.getMessage());

        //test when patient tries to book another appointment at the same date and time
        IllegalArgumentException ex5 = assertThrows(IllegalArgumentException.class, () -> instance.bookAppointment("schedule-9", "patient-1")); //patient 1 tries to book another appointment at the same date and time
        assertEquals("Patient already booked for an appointment at chosen time", ex5.getMessage());
    }

    @Test
    void testCancelAppointment() {
        System.out.println("cancelAppointment");
        ClinicData instance = new ClinicData();

        Appointment apt = instance.bookAppointment("schedule-1", "patient-1"); //book appointment first
        instance.cancelAppointment(apt.getBookingId(), apt.getPatientId()); //cancel the same appointment

        Appointment getApt = instance.getAppointment(apt.getBookingId());
        String result = getApt.getStatus();
        String expected = "CANCELLED";
        assertEquals(expected, result);

        //Test when patient tries to cancel already cancelled appointment
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> instance.cancelAppointment(apt.getBookingId(), apt.getPatientId()));
        assertEquals("Appointment already cancelled", ex.getMessage());

        //book another appointment
        Appointment apt2 = instance.bookAppointment("schedule-2", "patient-1");
        instance.attendAppointment(apt2.getBookingId(), apt2.getPatientId());

        //Test when patient tries to cancel appointment they are not booked in
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> instance.cancelAppointment(apt2.getBookingId(), "patient-4"));
        assertEquals("Patient unauthorized to cancel this appointment", ex2.getMessage());

        //Test when patient tries to cancel an attended appointment
        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> instance.cancelAppointment(apt2.getBookingId(), apt2.getPatientId()));
        assertEquals("Cannot cancel attended appointment", ex3.getMessage());

    }

    @Test
    void testAttendAppointment() {
        System.out.println("attendAppointment");
        ClinicData instance = new ClinicData();

        Appointment apt = instance.bookAppointment("schedule-1", "patient-1"); //book appointment first
        instance.attendAppointment(apt.getBookingId(), apt.getPatientId()); //cancel the same appointment

        Appointment getApt = instance.getAppointment(apt.getBookingId());
        String result = getApt.getStatus();
        String expected = "ATTENDED";
        assertEquals(expected, result);

        //Test when patient tries to attend already attended appointment
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> instance.attendAppointment(apt.getBookingId(), apt.getPatientId()));
        assertEquals("Appointment already attended", ex.getMessage());

        //book another appointment
        Appointment apt2 = instance.bookAppointment("schedule-2", "patient-1");
        instance.cancelAppointment(apt2.getBookingId(), apt2.getPatientId());

        //Test when patient tries to cancel appointment they are not booked in
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> instance.attendAppointment(apt2.getBookingId(), "patient-4"));
        assertEquals("Patient unauthorized to attend this appointment", ex2.getMessage());

        //Test when patient tries to attend a cancelled appointment
        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> instance.attendAppointment(apt2.getBookingId(), apt2.getPatientId()));
        assertEquals("Cannot attend cancelled appointment", ex3.getMessage());
    }

    @Test
    void testChangeAppointment() {
        System.out.println("changeAppointment");
        ClinicData instance = new ClinicData();

        Appointment apt = instance.bookAppointment("schedule-1", "patient-1"); //book appointment first
        Appointment changeApt = instance.changeAppointment(apt.getBookingId(), "patient-1", "schedule-2"); //change apt

        Appointment oldApt = instance.getAppointment(apt.getBookingId());
        String expected = "CANCELLED"; //expect old appointment to be cancelled
        assertEquals(expected, oldApt.getStatus());

        expected = "BOOKED"; //expect new appointment to be booked
        assertEquals(expected, changeApt.getStatus());
    }

    @Test
    void testGetAppointment() {
        System.out.println("getAppointment");
        ClinicData instance = new ClinicData();

        Appointment apt = instance.bookAppointment("schedule-1", "patient-1");
        Appointment getApt = instance.getAppointment(apt.getBookingId());

        assertEquals(apt.getBookingId(), getApt.getBookingId());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> instance.getAppointment("incorrectId"));
        assertEquals("Appointment is invalid", ex.getMessage());
    }

    @Test
    void testGetAvailableAppointmentsByExpertise() {
        System.out.println("getAvailableAppointmentsByExpertise");
        ClinicData instance = new ClinicData();

        ArrayList<Schedule> availableApts = instance.getAvailableAppointmentsByExpertise("Physiotherapy");
        int expected = 4; //initially 4 available appointments for physiotherapy expertise
        int result = availableApts.size();
        assertEquals(expected, result);

        Appointment bookedApt = instance.bookAppointment("schedule-1", "patient-1"); //book an appointment under physiotherapy
        ArrayList<Schedule> newAvailableApts = instance.getAvailableAppointmentsByExpertise("Physiotherapy");
        expected = 3; //after booking number of available appointments should reduce by 1
        result = newAvailableApts.size();
        assertEquals(expected, result);

        instance.cancelAppointment(bookedApt.getBookingId(), bookedApt.getPatientId()); //cancel booked appointment
        ArrayList<Schedule> newAvailableApts2 = instance.getAvailableAppointmentsByExpertise("Physiotherapy");
        expected = 4; //after canceling, the appointment should be freed up
        result = newAvailableApts2.size();
        assertEquals(expected, result);
    }

    @Test
    void testGetAvailableAppointmentsByPhysiotherapistName() {
        System.out.println("getAvailableAppointmentsByPhysiotherapistName");
        ClinicData instance = new ClinicData();

        ArrayList<Schedule> availableApts = instance.getAvailableAppointmentsByPhysiotherapistName("John Doe"); //John Does is also physio-1
        int expected = 4; //initially 4 available appointments for John Doe
        int result = availableApts.size();
        assertEquals(expected, result);

        Appointment bookedApt = instance.bookAppointment("schedule-1", "patient-1"); //book an appointment under John Doe
        ArrayList<Schedule> newAvailableApts = instance.getAvailableAppointmentsByExpertise("Physiotherapy");
        expected = 3; //after booking number of available appointments should reduce by 1
        result = newAvailableApts.size();
        assertEquals(expected, result);

        instance.cancelAppointment(bookedApt.getBookingId(), bookedApt.getPatientId()); //cancel booked appointment
        ArrayList<Schedule> newAvailableApts2 = instance.getAvailableAppointmentsByPhysiotherapistName("John Doe");
        expected = 4; //after canceling, the appointment should be freed up
        result = newAvailableApts2.size();
        assertEquals(expected, result);
    }

    @Test
    void testGetAppointmentsSize() {
        System.out.println("getAppointmentsSize");
        ClinicData instance = new ClinicData();
        int result = instance.getAppointmentsSize();
        int expected = 0;
        assertEquals(expected, result);

        instance.bookAppointment("schedule-1", "patient-1");
        result = instance.getAppointmentsSize();
        expected = 1;
        assertEquals(expected, result);
    }

    @Test
    void testGetPatientAppointments() {
        System.out.println("getPatientAppointments");
        ClinicData instance = new ClinicData();

        int result = instance.getPatientAppointments("patient-1").size();
        int expected = 0;
        assertEquals(expected, result);

        instance.bookAppointment("schedule-9", "patient-1");
        result = instance.getPatientAppointments("patient-1").size();
        expected = 1;
        assertEquals(expected, result);
    }

    @Test
    void testGetBookedAppointmentsByExpertise() {
        System.out.println("getBookedAppointmentsByExpertise");
        ClinicData instance = new ClinicData();

        int expected = 0; //we expect 0 booked appointments initially
        int result = instance.getBookedAppointmentsByExpertise("Physiotherapy").size();
        assertEquals(expected, result);

        Appointment apt = instance.bookAppointment("schedule-1", "patient-1"); //schedule-1 is under physiotherapy
        expected = 1; //should increase to 1 after booking
        result = instance.getBookedAppointmentsByExpertise("Physiotherapy").size();
        assertEquals(expected, result);

        instance.cancelAppointment(apt.getBookingId(), apt.getPatientId());
        expected = 0; //should decrease after cancelling
        result = instance.getBookedAppointmentsByExpertise("Physiotherapy").size();
        assertEquals(expected, result);
    }

    @Test
    void testGetBookedAppointmentsByPhysiotherapistName() {
        System.out.println("getBookedAppointmentsByPhysiotherapistName");
        ClinicData instance = new ClinicData();

        int expected = 0; //we expect 0 booked appointments initially
        int result = instance.getBookedAppointmentsByPhysiotherapistName("John Doe").size();
        assertEquals(expected, result);

        Appointment apt = instance.bookAppointment("schedule-1", "patient-1"); //schedule-1 is under John Doe
        expected = 1; //should increase to 1 after booking
        result = instance.getBookedAppointmentsByPhysiotherapistName("John Doe").size();
        assertEquals(expected, result);

        instance.cancelAppointment(apt.getBookingId(), apt.getPatientId());
        expected = 0; //should decrease after cancelling
        result = instance.getBookedAppointmentsByPhysiotherapistName("John Doe").size();
        assertEquals(expected, result);
    }

    @Test
    void testGetBookedAppointmentsByPatientId() {
        System.out.println("getBookedAppointmentsByPatientId");
        ClinicData instance = new ClinicData();

        int expected = 0; //we expect 0 booked appointments initially
        int result = instance.getBookedAppointmentsByPatientId("patient-1").size();
        assertEquals(expected, result);

        Appointment apt = instance.bookAppointment("schedule-1", "patient-1");
        expected = 1; //should increase to 1 after booking
        result = instance.getBookedAppointmentsByPatientId("patient-1").size();
        assertEquals(expected, result);

        instance.cancelAppointment(apt.getBookingId(), apt.getPatientId());
        expected = 0; //should decrease after cancelling
        result = instance.getBookedAppointmentsByPatientId("patient-1").size();
        assertEquals(expected, result);
    }
}