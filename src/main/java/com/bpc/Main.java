package com.bpc;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//comments (extra marks)
public class Main {

    public static void main(String[] args) {
        ClinicData clinicData = new ClinicData();
        System.out.println(clinicData.physiotherapists.get(2).expertise.getFirst().treatments.getFirst().name);

        clinicData.bookAppointment(10,3,2,"patient-2","physio-1");
        clinicData.bookAppointment(10,3,2,"patient-3","physio-2");
        System.out.println(clinicData.appointments.get(1).room);
    }
}