package com.bpc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ClinicDataTest {
    @Test
    void testAddPatient() {
        System.out.println("addPatient");
        ClinicData instance = new ClinicData();
        int totalNumOfPatients = instance.getTotalEverNumOfPatients();
        instance.addPatient("Abedi Pele","Some address","+44904438373");

        int  expected = totalNumOfPatients+1;
        int result = instance.getTotalEverNumOfPatients();
        assertEquals(expected,result); //expect total number of patients to increase by 1
    }

    @Test
    void getTotalEverNumOfPatients() {
        System.out.println("addPatient");
        ClinicData instance = new ClinicData();
        int result = instance.getTotalEverNumOfPatients();
        int expected = 10; // 10 patients get loaded via ClinicData constructor initially
        assertEquals(expected,result); //expect the initial number of patients to be 10

        //test when another patient is added
        instance.addPatient("Abedi Pele","Some address","+44904438373");
        expected++; //Number of patients should increase by 1
        result = instance.getTotalEverNumOfPatients();
        assertEquals(expected,result);
    }

    @Test
    void testBookAppointment() {
        System.out.println("bookAppointment");
        ClinicData instance = new ClinicData();
        int totalNumOfAppointments = instance.getTotalNumOfAppointments();

        instance.bookAppointment("2025-02-03 10","patient-1","physio-1");

        int  expected = totalNumOfAppointments+1;
        int result = instance.getTotalNumOfAppointments();
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
    }

    @Test
    void getTotalNumOfAppointments() {
    }
}