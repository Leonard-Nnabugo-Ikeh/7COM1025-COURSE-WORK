import java.util.ArrayList;

public class Physiotherapist extends Personnel{
    public ArrayList<Expertise> expertise;

    Physiotherapist(String fullName, String address, String phone,ArrayList<Physiotherapist> allPhysiotherapists) {
        super(fullName, address, phone, "physiotherapist");
        this.id = generatePhysioId(allPhysiotherapists);
    }

    private String generatePhysioId(ArrayList<Physiotherapist> allPhysiotherapists){
            if(allPhysiotherapists.isEmpty()){
                return "physio-1";
            }
            int physioNumber = Integer.parseInt(allPhysiotherapists.getLast().id.split("-")[1]);
            int newPhysioNumber = physioNumber + 1;
            return "physio-"+newPhysioNumber;
    };
}
