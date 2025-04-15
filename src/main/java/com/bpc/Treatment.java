package com.bpc;

public class Treatment {
    private String name;
    private String expertise;

    Treatment(String name, String expertise) {
        this.name = name;
        this.expertise = expertise;
    }

    public String getName() {
        return name;
    }
}
