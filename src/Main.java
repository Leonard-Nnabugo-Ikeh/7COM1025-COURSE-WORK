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

        Physiotherapist physio = new Physiotherapist("Leonard Ikeh","Some address","+448948576474");
        Patient patient = new Patient("John Doe","Some other address","+447843685346");

        System.out.println("Personnel "+physio.fullName+" is a "+physio.personnelType);
        System.out.println("Personnel "+patient.fullName+" is a "+patient.personnelType);
    }
}