package com.bpc;

import java.util.ArrayList;

public class Expertise {
    public String name;
    public ArrayList<Treatment> treatments;

    Expertise(String name, String[] treatmentNames) {
        this.name = name;
        this.treatments = new ArrayList<>();

        for (String names : treatmentNames) {
            this.treatments.add(new Treatment(names));
        }
    }

    public void addTreatment(String treatmentName){
        this.treatments.add(new Treatment(treatmentName));
    }
}
