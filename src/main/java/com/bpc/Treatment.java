package com.bpc;

public class Treatment {
    private final String name;
    private final String expertise;

    Treatment(String name, String expertise) {
        this.name = name;
        this.expertise = expertise;
    }

    public String getName() {
        return name;
    }

    public String getExpertise() {
        return expertise;
    }
}
