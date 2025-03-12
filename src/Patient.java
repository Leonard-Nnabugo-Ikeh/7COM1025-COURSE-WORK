public class Patient extends Personnel {
    Patient(String fullName, String address, String phone, int totalEverNumOfPatients) {
        super(fullName, address, phone, "patient", totalEverNumOfPatients);
    }
}
