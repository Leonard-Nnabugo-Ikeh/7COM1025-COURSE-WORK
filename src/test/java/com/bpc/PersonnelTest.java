package com.bpc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonnelTest {

    @Test
    void testGeneratePersonnelId() {
        int totalEverNumOfPatients = 6;
        Personnel instance = new Personnel("Leonard", "Some address", "+448084744828", "patient", totalEverNumOfPatients);
        String personnelId = instance.generatePersonnelId("patient", totalEverNumOfPatients);
        assertEquals("patient-6", personnelId);

        int totalEverNumOfPhysios = 7;
        Personnel instance2 = new Personnel("Leonard", "Some address", "+448084744828", "physiotherapist", totalEverNumOfPhysios);
        String personnelId2 = instance2.generatePersonnelId("physiotherapist", totalEverNumOfPhysios);
        assertEquals("physio-7", personnelId2);
    }
}