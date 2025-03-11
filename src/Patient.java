import java.util.ArrayList;

public class Patient extends Personnel {
    Patient(String fullName, String address, String phone, ArrayList<Patient> allPatients) {
        super(fullName, address, phone, "patient");
        this.id = generatePatientId(allPatients);
    }

    private String generatePatientId(ArrayList<Patient> allPatients) {
        if(allPatients.isEmpty()){
            return "patient-1";
        }
        int patientNumber = Integer.parseInt(allPatients.getLast().id.split("-")[1]);
        int newPatientNumber = patientNumber + 1;
        return "patient-"+newPatientNumber;
    };
}
