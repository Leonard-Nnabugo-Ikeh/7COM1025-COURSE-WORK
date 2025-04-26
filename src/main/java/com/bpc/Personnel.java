package com.bpc;/*
 * Possible tests:
 * personnelType can only be: "patient" or "physiotherapist"
 * */

public class Personnel {
    private final String id, fullName, address, phone;

    protected Personnel(String fullName, String address, String phone, String personnelType, int totalEverNumOfPersonnel) {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.id = generatePersonnelId(personnelType, totalEverNumOfPersonnel);
    }


    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String generatePersonnelId(String personnelType, int totalEverNumOfPersonnel) {
        if (personnelType.equals("physiotherapist")) {
            return "physio-" + (totalEverNumOfPersonnel);
        }
        return "patient-" + (totalEverNumOfPersonnel + 1);
    }
}
