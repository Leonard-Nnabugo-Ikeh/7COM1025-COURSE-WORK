package com.bpc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClinicData {
    private final ArrayList<Physiotherapist> physiotherapists = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private int totalEverNumOfPhysios = 0,totalEverNumOfPatients = 0;

    ClinicData() {
        loadMockData();
    }

    public void addPhysiotherapist(Physiotherapist p) {
        this.physiotherapists.add(p);
        this.totalEverNumOfPhysios++;
    }

    public void addPatient(Patient p) {
        this.patients.add(p);
        this.totalEverNumOfPatients++;
    }

    public ArrayList<Physiotherapist> getPhysiotherapists() {
        return physiotherapists;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public int getTotalEverNumOfPhysios() {
        return totalEverNumOfPhysios;
    }

    public void setTotalEverNumOfPhysios(int totalEverNumOfPhysios) {
        this.totalEverNumOfPhysios = totalEverNumOfPhysios;
    }

    public int getTotalEverNumOfPatients() {
        return totalEverNumOfPatients;
    }

    public void setTotalEverNumOfPatients(int totalEverNumOfPatients) {
        this.totalEverNumOfPatients = totalEverNumOfPatients;
    }

    public void bookAppointment() {
    }

    private void loadMockData(){
        String filePath = "src/main/java/com/bpc/mock-data.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Load physiotherapist data
                if (line.contains("##")) {
                    this.addPhysiotherapist(getPhysioFromTxt(line));
                }
                //load patients data
                if(line.contains("--")){
                    this.addPatient(getPatientFromTxt(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Physiotherapist getPhysioFromTxt(String line){
        String [] infoList = line.split("##")[1].trim().split("/");
        String fullName = infoList[0].trim();
        String address = infoList[1].trim();
        String phone = infoList[2].trim();

        //create physiotherapist instance
        Physiotherapist physio = new Physiotherapist(fullName,address,phone,totalEverNumOfPhysios);

        //Get expertise information for physio
        String [] expertiseInfoList = infoList[3].trim().split("~~");

        for(String expertiseInfo : expertiseInfoList){
            String[] arr = expertiseInfo.split("~");
            String expertiseName = arr[0].trim();
            String[] treatmentNames = arr[1].trim().split(",");

            for(String treatmentName : treatmentNames){
                physio.addTreatment(treatmentName,expertiseName);
            }
        }
        return physio;
    }

    private Patient getPatientFromTxt(String line){
        String [] infoList = line.split("--")[1].trim().split("/");
        String fullName = infoList[0].trim();
        String address = infoList[1].trim();
        String phone = infoList[2].trim();

        return new Patient(fullName,address,phone,totalEverNumOfPatients);
    }
}
