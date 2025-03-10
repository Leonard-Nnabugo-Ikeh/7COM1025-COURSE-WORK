import java.util.ArrayList;

public class Physiotherapist extends Personnel{
    public ArrayList<Expertise> expertise;

    Physiotherapist(String fullName, String address, String phone) {
        super(fullName, address, phone, "physiotherapist");
    }

    private String generatePhysiotherapistId(){
        //Not yet implemented
        return "";
    }
}
