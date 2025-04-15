package com.bpc;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//comments (extra marks)
public class Main {

    public static void main(String[] args) {
        ClinicData clinicData = new ClinicData();
        System.out.println(clinicData.getPhysiotherapists().getLast().getTreatments().getFirst().getName());
    }
}