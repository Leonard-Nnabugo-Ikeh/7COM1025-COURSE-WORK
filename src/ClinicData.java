import java.util.ArrayList;

public class ClinicData {
    public ArrayList<Physiotherapist> physiotherapists;
    public ArrayList<Patient> patients;
    public int totalEverNumOfPhysios = 0,totalEverNumOfPatients = 0;

    ClinicData() {
        this.physiotherapists = new ArrayList<>();
        this.patients = new ArrayList<>();

        this.physiotherapists.add(new Physiotherapist("Wayne Rooney","2, Wayne Street","+44859837474",this.physiotherapists));
        this.physiotherapists.add(new Physiotherapist("Diogo Dalot","12, Dalot street","+44859837474",this.physiotherapists));

        this.patients.add(new Patient("Juan Mata","1, Juan Street","+4488479074",this.patients));
        this.patients.add(new Patient("Bruno Fernandes","1, Bruno Street","+4488479074",this.patients));
    }

}
