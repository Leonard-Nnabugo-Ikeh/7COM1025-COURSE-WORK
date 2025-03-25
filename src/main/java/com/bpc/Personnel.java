package com.bpc;/*
* Possible tests:
* personnelType can only be: "patient" or "physiotherapist"
* */

public class Personnel{
    private String id,fullName,address,phone,personnelType;

     protected Personnel(String fullName, String address, String phone, String personnelType,int totalEverNumOfPersonnel) {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.personnelType = personnelType;
        this.id = generatePersonnelId(personnelType,totalEverNumOfPersonnel);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }

    private String generatePersonnelId(String personnelType, int totalEverNumOfPersonnel){
         if(personnelType.equals("physiotherapist")){
             return "physio-"+(totalEverNumOfPersonnel+1);
         }
         return "patient-"+(totalEverNumOfPersonnel+1);
    }
}
