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
        instance.addPatient("Abedi Pele","Some address","+44904438373");

        int  expected = numOfPatients+1; //expect total number of patients to increase by 1
        int result = instance.getPatientsSize();
        assertEquals(expected,result);
    }

    @Test
    void testGetPatient(){
        System.out.println("getPatient");
        ClinicData instance = new ClinicData();

        Patient pat = instance.addPatient("Abedi Pele","Some address","+44904438373");
        Patient getPat = instance.getPatient(pat.getId());

        assertEquals(pat.getId(),getPat.getId());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()->instance.getPatient("incorrectId"));
        assertEquals("Patient is invalid",ex.getMessage());
    }

    @Test
    void testRemovePatient() {
        System.out.println("removePatient");
        ClinicData instance = new ClinicData();
        int numOfPatients = instance.getPatientsSize();
        instance.bookAppointment("2025-02-03 10","patient-1","physio-1");
        instance.removePatient("patient-1");

        int expected = numOfPatients-1; //expect total number of patients to decrease by 1
        int result = instance.getPatientsSize();
        assertEquals(expected,result);

        //check if patients appointments remain
        ArrayList<Appointment> patientAppointments = instance.getPatientAppointments("patient-1");
        expected = 1;
        result = patientAppointments.size();
        assertEquals(expected,result);

        //check if all patients appointments are cancelled
        patientAppointments.forEach(a->assertEquals("CANCELLED", a.getStatus()));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()->instance.removePatient("incorrectId"));
        assertEquals("Patient is invalid",ex.getMessage());
    }

    @Test
    void testGetPatientsSize() {
        System.out.println("getPatientsSize");
        ClinicData instance = new ClinicData();
        int result = instance.getPatientsSize();
        int expected = 10; // 10 patients get loaded via ClinicData constructor initially
        assertEquals(expected,result); //expect the initial number of patients to be 10

        //test when another patient is added
        instance.addPatient("Abedi Pele","Some address","+44904438373");
        expected++; //Number of patients should increase by 1
        result = instance.getPatientsSize();
        assertEquals(expected,result);
    }

    @Test
    void testBookAppointment() {
        System.out.println("bookAppointment");
        ClinicData instance = new ClinicData();
        int totalNumOfAppointments = instance.getAppointmentsSize();

        instance.bookAppointment("2025-02-03 10","patient-1","physio-1");

        int expected = totalNumOfAppointments+1;
        int result = instance.getAppointmentsSize();
        assertEquals(expected,result); //passes of appointment got booked

        //Test when an invalid patient id is passed
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> instance.bookAppointment("2025-02-03 10","pat-1","physio-1")); //pat-1 is not an available patient id
        assertEquals("Patient is invalid", ex.getMessage());

        //Test when an invalid physiotherapist id is passed
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,()-> instance.bookAppointment("2025-02-03 10","patient-1","physio-100")); //physio-100 is not an available physio id
        assertEquals("Physiotherapist is invalid", ex2.getMessage());

        //test for when a schedule is not available for a physiotherapist
        IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class,()-> instance.bookAppointment("2025-02-03 12","patient-1","physio-1")); //this datetime is not part of the physios schedule
        assertEquals("Schedule not available for physiotherapist", ex3.getMessage());

        //test when the schedule has already been booked or attended
        IllegalArgumentException ex4 = assertThrows(IllegalArgumentException.class,()-> instance.bookAppointment("2025-02-03 10","patient-2","physio-1")); //this was already booked by patient-1 in our first edge-case
        assertEquals("Appointment already booked or attended", ex4.getMessage());

        //test when patient tries to book the same appointment twice
        IllegalArgumentException ex5 = assertThrows(IllegalArgumentException.class,()-> instance.bookAppointment("2025-02-03 10","patient-1","physio-1"));
        assertEquals("Appointment already booked or attended", ex5.getMessage());

        //test when patient tries to book another appointment at the same date and time
        IllegalArgumentException ex6 = assertThrows(IllegalArgumentException.class,()-> instance.bookAppointment("2025-02-03 10","patient-1","physio-3")); //patient 1 tries to book another appointment at the same date and time
        assertEquals("Patient already booked for an appointment at chosen time", ex6.getMessage());
    }

    @Test
    void testCancelAppointment() {
        System.out.println("cancelAppointment");
        ClinicData instance = new ClinicData();

        Appointment apt = instance.bookAppointment("2025-02-03 10","patient-1","physio-1"); //book appointment first
        instance.cancelAppointment(apt.getBookingId(),apt.getPatientId()); //cancel the same appointment

        Appointment getApt = instance.getAppointment(apt.getBookingId());
        String result = getApt.getStatus();
        String expected = "CANCELLED";
        assertEquals(expected,result);

        //Test when patient tries to cancel already cancelled appointment
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> instance.cancelAppointment(apt.getBookingId(),apt.getPatientId()));
        assertEquals("Appointment already cancelled", ex.getMessage());

        //book another appointment
        Appointment apt2 = instance.bookAppointment("2025-02-04 14","patient-1","physio-1");
        instance.attendAppointment(apt2.getBookingId(),apt2.getPatientId());

        //Test when patient tries to cancel an attended appointment
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,()-> instance.cancelAppointment(apt2.getBookingId(),apt2.getPatientId()));
        assertEquals("Cannot cancel attended appointment", ex2.getMessage());
    }

    @Test
    void testAttendAppointment() {
        System.out.println("attendAppointment");
        ClinicData instance = new ClinicData();

        Appointment apt = instance.bookAppointment("2025-02-03 10","patient-1","physio-1"); //book appointment first
        instance.attendAppointment(apt.getBookingId(),apt.getPatientId()); //cancel the same appointment

        Appointment getApt = instance.getAppointment(apt.getBookingId());
        String result = getApt.getStatus();
        String expected = "ATTENDED";
        assertEquals(expected,result);

        //Test when patient tries to attend already attended appointment
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> instance.attendAppointment(apt.getBookingId(),apt.getPatientId()));
        assertEquals("Appointment already attended", ex.getMessage());

        //book another appointment
        Appointment apt2 = instance.bookAppointment("2025-02-04 14","patient-1","physio-1");
        instance.cancelAppointment(apt2.getBookingId(),apt2.getPatientId());

        //Test when patient tries to attend a cancelled appointment
        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class,()-> instance.attendAppointment(apt2.getBookingId(),apt2.getPatientId()));
        assertEquals("Cannot attend cancelled appointment", ex2.getMessage());
    }

    @Test
    void testChangeAppointment() {
        System.out.println("changeAppointment");
        ClinicData instance = new ClinicData();

        Appointment apt = instance.bookAppointment("2025-02-03 10","patient-1","physio-1"); //book appointment first
        Appointment changeApt = instance.changeAppointment(apt.getBookingId(),"patient-1","2025-02-04 14","physio-1"); //change apt

        Appointment oldApt = instance.getAppointment(apt.getBookingId());
        String expected = "CANCELLED"; //expect old appointment to be cancelled
        assertEquals(expected,oldApt.getStatus());

        expected = "BOOKED"; //expect new appointment to be booked
        assertEquals(expected,changeApt.getStatus());
    }

    @Test
    void testGetAppointment() {
        System.out.println("getAppointment");
        ClinicData instance = new ClinicData();

        Appointment apt = instance.bookAppointment("2025-02-03 10","patient-1","physio-1");
        Appointment getApt = instance.getAppointment(apt.getBookingId());

        assertEquals(apt.getBookingId(),getApt.getBookingId());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,()-> instance.getAppointment("incorrectId"));
        assertEquals("Appointment is invalid", ex.getMessage());
    }

    @Test
    void testGetAppointmentsSize() {
        System.out.println("getAppointmentsSize");
        ClinicData instance = new ClinicData();
        int result = instance.getAppointmentsSize();
        int expected = 0;
        assertEquals(expected,result);

        instance.bookAppointment("2025-02-03 10","patient-1","physio-1");
        result = instance.getAppointmentsSize();
        expected = 1;
        assertEquals(expected,result);
    }

    @Test
    void testGetPatientAppointments() {
        System.out.println("getPatientAppointments");
        ClinicData instance = new ClinicData();

        int result = instance.getPatientAppointments("patient-1").size();
        int expected = 0;
        assertEquals(expected,result);

        instance.bookAppointment("2025-02-03 10","patient-1","physio-3");
        result = instance.getPatientAppointments("patient-1").size();
        expected = 1;
        assertEquals(expected,result);
    }
}