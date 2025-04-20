package com.bpc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClinicData {
    private final ArrayList<Physiotherapist> physiotherapists = new ArrayList<>();
    private final ArrayList<Patient> patients = new ArrayList<>();
    private final ArrayList<Appointment> appointments = new ArrayList<>();
    private int totalEverNumOfPatients = 0, totalEverNumOfAppointments = 0;
    private final Validation validation = new Validation();

    ClinicData() {
        loadMockData();
    }

    public Patient getPatient(String patientId){
        return this.patients.stream().filter(p->p.getId().equals(patientId)).findFirst().orElseThrow(()->new IllegalArgumentException("Patient is invalid"));
    }

    public Patient addPatient(String fullName, String address, String phone) {
        Patient pat = new Patient(fullName,address,phone,totalEverNumOfPatients);
        this.patients.add(pat);
        this.totalEverNumOfPatients++;
        return pat;
    }

    public void removePatient(String patientId) {
        if(!validation.isPatientValid(this.patients,patientId))throw new IllegalArgumentException("Patient is invalid");

        //cancel all patients appointments
        this.appointments.forEach(a->{
            if(a.getPatientId().equals(patientId)){
                a.setStatus("CANCELLED");
            }
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

    public Physiotherapist getPhysiotherapist(String physioId){
        return this.physiotherapists.stream().filter(p->p.getId().equals(physioId)).findFirst().orElseThrow(()->new IllegalArgumentException("Physiotherapist is invalid"));
    }

    public Appointment getAppointment(String bookingId){
       return this.appointments.stream().filter(a->a.getBookingId().equals(bookingId)).findFirst().orElseThrow(()->new IllegalArgumentException("Appointment is invalid"));
    }

    public Schedule getScheduleForBooking(String patientId, String dateTime, String physioId) {
        if(!validation.isPatientValid(this.patients,patientId)) throw new IllegalArgumentException("Patient is invalid");

        Physiotherapist physio = getPhysiotherapist(physioId); //throws exception if physio is invalid

        //check schedule
        Schedule schedule = physio.getSchedule(dateTime);
        if(schedule==null) throw new IllegalArgumentException("Schedule not available for physiotherapist");

        //check if appointment is booked or attended
        boolean isBookedOrAttended = validation.appointmentIsBookedOrAttended(this.appointments,dateTime,physioId);
        if(isBookedOrAttended) throw new IllegalArgumentException("Appointment already booked or attended");

        //check if patient has already booked an appointment at that specific time of the date
        boolean patientBookedAtDatetime = validation.patientIsBookedAtDatetime(this.appointments,patientId,dateTime);
        if(patientBookedAtDatetime) throw new IllegalArgumentException("Patient already booked for an appointment at chosen time");

        return schedule;
    }

    public Appointment bookAppointment(String dateTime,String patientId,String physioId) {
        Schedule schedule = this.getScheduleForBooking(patientId,dateTime,physioId);

        Appointment appointment = new Appointment(patientId,schedule,totalEverNumOfAppointments); //Appointment instance
        this.appointments.add(appointment); //add appointment
        this.totalEverNumOfAppointments++;

        return appointment;
    }

    public void cancelAppointment(String bookingId, String patientId) {
        if(!validation.isPatientValid(this.patients,patientId)) throw new IllegalArgumentException("Patient is invalid");
        Appointment apt = getAppointment(bookingId);

        if(!apt.getPatientId().equals(patientId)) throw new IllegalArgumentException("Unauthorized");
        if(apt.getStatus().equals("CANCELLED")) throw new IllegalArgumentException("Appointment already cancelled");
        if(apt.getStatus().equals("ATTENDED")) throw new IllegalArgumentException("Cannot cancel attended appointment");

        this.appointments.forEach(a->{
            if(a.getBookingId().equals(bookingId)) a.setStatus("CANCELLED");
        });
    }

    public void attendAppointment(String bookingId, String patientId) {
        if(!validation.isPatientValid(this.patients,patientId)) throw new IllegalArgumentException("Patient is invalid");
        Appointment apt = getAppointment(bookingId);

        if(!apt.getPatientId().equals(patientId)) throw new IllegalArgumentException("Unauthorized");
        if(apt.getStatus().equals("ATTENDED")) throw new IllegalArgumentException("Appointment already attended");
        if(apt.getStatus().equals("CANCELLED")) throw new IllegalArgumentException("Cannot attend cancelled appointment");

        this.appointments.forEach(a->{
            if(a.getBookingId().equals(bookingId)) a.setStatus("ATTENDED");
        });
    }

    public Appointment changeAppointment(String oldBookingId, String patientId, String newAptDatetime, String newAptPhysioId) {
        Schedule schedule = this.getScheduleForBooking(patientId,newAptDatetime,newAptPhysioId);

        cancelAppointment(oldBookingId,patientId);

        Appointment appointment = new Appointment(patientId,schedule,totalEverNumOfAppointments);
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
