import java.util.ArrayList;

public class Physiotherapist extends Personnel{
    public ArrayList<Expertise> expertise;

    Physiotherapist(String fullName, String address, String phone,int totalEverNumOfPhysios) {
        super(fullName, address, phone, "physiotherapist", totalEverNumOfPhysios);
    }
}
