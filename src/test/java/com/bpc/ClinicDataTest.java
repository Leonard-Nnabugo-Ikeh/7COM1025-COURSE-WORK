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
    }

    @Test
    void testBookAppointment() {
    }
}