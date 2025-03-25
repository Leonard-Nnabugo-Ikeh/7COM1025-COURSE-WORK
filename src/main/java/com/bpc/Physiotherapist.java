package com.bpc;

import java.util.ArrayList;

public class Physiotherapist extends Personnel{
    final ArrayList<Expertise> expertise = new ArrayList<>();

    Physiotherapist(String fullName, String address, String phone,int totalEverNumOfPhysios) {
        super(fullName, address, phone, "physiotherapist", totalEverNumOfPhysios);
    }

    public ArrayList<Expertise> getExpertise() {
        return expertise;
    }

    public void addExpertise(Expertise expertise) {
        this.expertise.add(expertise);
    }
}
