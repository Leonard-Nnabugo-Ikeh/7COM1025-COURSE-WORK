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

        int expected = instance.getTotalEverNumOfPatients();
        assertEquals(expected,totalNumOfPatients+1);
    }

    @Test
    void getTotalEverNumOfPatients() {
        System.out.println("addPatient");
        ClinicData instance = new ClinicData();
        int expected = instance.getTotalEverNumOfPatients();
        int actual = 10; // 10 patients get loaded via ClinicData constructor initially

        assertEquals(expected,actual);

        //test when another patient is added
        instance.addPatient("Abedi Pele","Some address","+44904438373");
        expected = instance.getTotalEverNumOfPatients();
        actual = 11;
        assertEquals(expected,actual);
    }

    @Test
    void testBookAppointment() {
    }
}