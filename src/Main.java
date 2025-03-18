//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//comments (extra marks)
public class Main {

    public static void main(String[] args) {
        Expertise expertise = new Expertise("Some Expertise",new String[] {"Some Treatment"});
        System.out.println("Main class");

        //test new classes
        System.out.println(expertise.treatments.getFirst().name);
        expertise.addTreatment("Another Treatment");
        System.out.println(expertise.treatments.get(1).name);

        ClinicData clinicData = new ClinicData();
        System.out.println(clinicData.physiotherapists.getFirst().expertise.getFirst().treatments.get(1).name);
    }
}