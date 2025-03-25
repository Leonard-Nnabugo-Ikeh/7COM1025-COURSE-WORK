package com.bpc;/*
* Possible tests:
* personnelType can only be: "patient" or "physiotherapist"
* */

public class Personnel{
    public String id,fullName,address,phone,personnelType;

     protected Personnel(String fullName, String address, String phone, String personnelType,int totalEverNumOfPersonnel) {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.personnelType = personnelType;
        this.id = generatePersonnelId(personnelType,totalEverNumOfPersonnel);
    }

    private String generatePersonnelId(String personnelType, int totalEverNumOfPersonnel){
         if(personnelType.equals("physiotherapist")){
             return "physio-"+(totalEverNumOfPersonnel+1);
         }
         return "patient-"+(totalEverNumOfPersonnel+1);
    }
}
