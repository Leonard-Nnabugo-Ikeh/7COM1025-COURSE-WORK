package com.bpc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClinicData {
    private final ArrayList<Physiotherapist> physiotherapists = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private int totalEverNumOfPatients = 0, totalEverNumOfAppointments = 0;

    ClinicData() {
        loadMockData();
    }

    public void addPatient(String fullName, String address, String phone) {
        this.patients.add(new Patient(fullName,address,phone,totalEverNumOfPatients));
        this.totalEverNumOfPatients++;
    }

    public void removePatient(String patientId) {
        //cancel patients appointments
        this.appointments.forEach(a->{
            if(a.getPatientId().equals(patientId)){
                a.setStatus("CANCELLED");
            };
        });
        //remove patient
        this.patients.removeIf(p->p.getId().equals(patientId));
    }

    public int getPatientsSize(){
        return this.patients.size();
    }

    public int getAppointmentsSize(){
        return this.appointments.size();
    }

    public ArrayList<Appointment> getPatientAppointments(String patientId) {
        return this.appointments.stream().filter(appointment -> appointment.getPatientId().equals(patientId)).collect(Collectors.toCollection((ArrayList::new)));
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

    public Appointment bookAppointment(String dateTime,String patientId,String physioId) {
        //check if patient is valid
        boolean validPatient = this.patients.stream().anyMatch(pat->pat.getId().equals(patientId));
        if(!validPatient) throw new IllegalArgumentException("Patient is invalid");

        //check if physio is valid
        Optional<Physiotherapist> physio = this.physiotherapists.stream().filter(p->p.getId().equals(physioId)).findFirst();
        if(physio.isEmpty()) throw new IllegalArgumentException("Physiotherapist is invalid");

        //check schedule
        Schedule schedule = physio.get().getSchedule(dateTime);
        if(schedule==null) throw new IllegalArgumentException("Schedule not available for physiotherapist");

        //check if appointment is booked or attended
        boolean isBookedOrAttended = this.appointments.stream().anyMatch(a->a.getSchedule().getDateTime().equals(dateTime)&&a.getSchedule().getPhysioId().equals(physioId) && (a.getStatus().equals("BOOKED") || a.getStatus().equals("ATTENDED")));
        if(isBookedOrAttended) throw new IllegalArgumentException("Appointment already booked or attended");

        //check if patient has already booked an appointment at that specific time of the date
        boolean patientBookedAtDatetime = this.appointments.stream().anyMatch(a->a.getPatientId().equals(patientId) && a.getSchedule().getDateTime().equals(dateTime) && (a.getStatus().equals("BOOKED") || a.getStatus().equals("ATTENDED")));
        if(patientBookedAtDatetime) throw new IllegalArgumentException("Patient already booked for an appointment at chosen time");

        Appointment appointment = new Appointment(patientId,schedule,totalEverNumOfAppointments); //Appointment instance
        this.appointments.add(appointment); //add appointment
        this.totalEverNumOfAppointments++;

        return appointment;
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
