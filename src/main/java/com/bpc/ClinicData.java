package com.bpc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ClinicData {
    private final ArrayList<Physiotherapist> physiotherapists = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private int totalEverNumOfPatients = 0;

    ClinicData() {
        loadMockData();
    }

    public void addPatient(String fullName, String address, String phone) {
        this.patients.add(new Patient(fullName,address,phone,totalEverNumOfPatients));
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
            int physioNumber = 1;
            while ((line = br.readLine()) != null) {
                // Load physiotherapist data
                if (line.contains("##")) {
                    this.addPhysioFromTxt(line,physioNumber);
                    physioNumber++;
                }
                //load patients data
                if(line.contains("--")){
                    this.addPatientFromTxt(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPhysioFromTxt(String line, int physioNumber) {
        String [] infoList = line.split("##")[1].trim().split("/");
        String fullName = infoList[0].trim();
        String address = infoList[1].trim();
        String phone = infoList[2].trim();

        //create physiotherapist instance
        Physiotherapist physio = new Physiotherapist(fullName,address,phone,physioNumber);

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
        this.physiotherapists.add(physio);
    }

    private void addPatientFromTxt(String line){
        String [] infoList = line.split("--")[1].trim().split("/");
        String fullName = infoList[0].trim();
        String address = infoList[1].trim();
        String phone = infoList[2].trim();

        this.addPatient(fullName,address,phone);
    }
}
