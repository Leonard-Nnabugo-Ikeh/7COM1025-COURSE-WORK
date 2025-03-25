package com.bpc;

import java.util.ArrayList;

public class Expertise {
    private String name;
    private ArrayList<Treatment> treatments;

    Expertise(String name, String[] treatmentNames) {
        this.name = name;
        this.treatments = new ArrayList<>();

        for (String names : treatmentNames) {
            this.treatments.add(new Treatment(names));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }

    public void addTreatment(String treatmentName){
        this.treatments.add(new Treatment(treatmentName));
    }
}
