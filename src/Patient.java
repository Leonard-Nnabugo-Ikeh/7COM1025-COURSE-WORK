public class Patient extends Personnel {
    Patient(String fullName, String address, String phone) {
        super(fullName, address, phone, "patient");
    }

    private String generatePhysiotherapistId(){
        //Not yet implemented
        return "";
    }
}
