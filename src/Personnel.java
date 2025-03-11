/*
* Possible tests:
* personnelType can only be: "patient" or "physiotherapist"
* */

public class Personnel{
    public String id,fullName,address,phone,personnelType;

     protected Personnel(String fullName, String address, String phone, String personnelType) {
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.personnelType = personnelType;
    }
}
