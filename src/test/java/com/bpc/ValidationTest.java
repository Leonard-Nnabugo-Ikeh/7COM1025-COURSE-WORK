package com.bpc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    @Test
    void testIsFullNameValid() {
        System.out.println("isFullNameValid");
        Validation instance = new Validation();

        boolean expected = false;
        boolean result = instance.isFullNameValid("A"); //single character test
        assertEquals(expected, result);

        result = instance.isFullNameValid("123Leonard"); //numbers in name
        assertEquals(expected, result);

        expected = true;
        result = instance.isFullNameValid("Leonard Ikeh"); //correct fullname
        assertEquals(expected, result);
    }

    @Test
    void testIsPhoneNumberValid() {
        System.out.println("isPhoneNumberValid");
        Validation instance = new Validation();

        boolean expected = false;
        boolean result = instance.isPhoneNumberValid("09043"); //not enough numbers for a phone number
        assertEquals(expected, result);

        result = instance.isPhoneNumberValid("09043uivhfldfs"); //letters in number
        assertEquals(expected, result);

        expected = true;
        result = instance.isPhoneNumberValid("09043987489"); //correct phone number
        assertEquals(expected, result);

        result = instance.isPhoneNumberValid("+449043987489"); //correct with country code
        assertEquals(expected, result);
    }

    @Test
    void testIsAddressValid() {
        System.out.println("isAddressValid");
        Validation instance = new Validation();

        boolean expected = false;
        boolean result = instance.isAddressValid("A"); //invalid address
        assertEquals(expected, result);

        expected = true;
        result = instance.isAddressValid("3 De Havilland, Mosquito Way, Hatfield"); //valid address
        assertEquals(expected, result);
    }

    @Test
    void testIsPatientValid() {
        System.out.println("isPatientValid");
        Validation instance = new Validation();
        ClinicData instance2 = new ClinicData();

        Patient pat = instance2.addPatient("Leonard Ikeh", "Some Address", "+447881161576");

        boolean expected = true;
        boolean result = instance.isPatientValid(instance2.getPatients(), pat.getId());
        assertEquals(expected, result);

        expected = false;
        result = instance.isPatientValid(instance2.getPatients(), "no-such-id");
        assertEquals(expected, result);
    }

    @Test
    void testAppointmentIsBookedOrAttended() {
        System.out.println("appointmentIsBookedOrAttended");
        Validation instance = new Validation();
        ClinicData instance2 = new ClinicData();

        boolean expected = false;
        boolean result = instance.appointmentIsBookedOrAttended(instance2.getAppointments(), "schedule-1"); //schedule-1 is not booked initially
        assertEquals(expected, result);

        instance2.bookAppointment("schedule-1", "patient-1"); //book schedule-5 for patient-1
        expected = true;
        result = instance.appointmentIsBookedOrAttended(instance2.getAppointments(), "schedule-1");
        assertEquals(expected, result);
    }

    @Test
    void testPatientIsBookedAtDatetime() {
        System.out.println("patientIsBookedAtDatetime");
        Validation instance = new Validation();
        ClinicData instance2 = new ClinicData();

        String dateTime = "2025-02-03 10";
        boolean expected = false;
        boolean result = instance.patientIsBookedAtDatetime(instance2.getAppointments(), "patient-1", dateTime);
        assertEquals(expected, result);

        instance2.bookAppointment("schedule-1", "patient-1"); //schedule-1 is scheduled at "2025-02-03 10" date time
        expected = true;
        result = instance.patientIsBookedAtDatetime(instance2.getAppointments(), "patient-1", dateTime);
        assertEquals(expected, result);
    }
}