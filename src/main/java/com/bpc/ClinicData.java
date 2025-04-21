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
    private final Validation validation = new Validation();

    ClinicData() {
        loadMockData();
    }

    public Patient getPatient(String patientId){
        return this.patients.stream().filter(p->p.getId().equals(patientId)).findFirst().orElseThrow(()->new IllegalArgumentException("Patient is invalid"));
    }

    public Patient addPatient(String fullName, String address, String phone) {
        if(!validation.isFullNameValid(fullName)) throw new IllegalArgumentException("Full name is invalid");
        if(!validation.isAddressValid(address)) throw new IllegalArgumentException("Address is invalid");
        if(!validation.isPhoneNumberValid(phone)) throw new IllegalArgumentException("Phone number is invalid");

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

    public Schedule getSchedule(String scheduleId) {
        Physiotherapist physiotherapist = this.physiotherapists.stream().filter(p -> p.getSchedule(scheduleId) != null).findFirst().orElseThrow(()->new IllegalArgumentException("Schedule not found"));
        return physiotherapist.getSchedule(scheduleId);
    }

    public Physiotherapist getPhysiotherapist(String physioId){
        return this.physiotherapists.stream().filter(p->p.getId().equals(physioId)).findFirst().orElseThrow(()->new IllegalArgumentException("Physiotherapist is invalid"));
    }

    public Schedule getScheduleForBooking(String patientId, String scheduleId) {
        if(!validation.isPatientValid(this.patients,patientId)) throw new IllegalArgumentException("Patient is invalid");

        Schedule schedule = this.getSchedule(scheduleId); // throws error if schedule is not found

        //check if appointment is booked or attended
        boolean isBookedOrAttended = validation.appointmentIsBookedOrAttended(this.appointments,scheduleId);
        if(isBookedOrAttended) throw new IllegalArgumentException("Appointment already booked or attended");

        //check if patient has already booked an appointment at that specific time of the date
        boolean patientBookedAtDatetime = validation.patientIsBookedAtDatetime(this.appointments,patientId,schedule.getDateTime() );
        if(patientBookedAtDatetime) throw new IllegalArgumentException("Patient already booked for an appointment at chosen time");

        return schedule;
    }

    public Appointment getAppointment(String bookingId){
        return this.appointments.stream().filter(a->a.getBookingId().equals(bookingId)).findFirst().orElseThrow(()->new IllegalArgumentException("Appointment is invalid"));
    }

    public Appointment getAppointment(String dateTime, String physioId){
        return this.appointments.stream().filter(a->a.getSchedule().getDateTime().equals(dateTime)).findFirst().orElseThrow(()->new IllegalArgumentException("Appointment is invalid"));
    }

    public ArrayList<Schedule> getAvailableAppointmentsByExpertise(String expertise) {
        ArrayList<Schedule> schedules = new ArrayList<>();

        physiotherapists.forEach(p->{
            p.getTimetable().forEach(s->{
                if(!validation.appointmentIsBookedOrAttended(this.appointments,s.getScheduleId()) && s.getTreatment().getExpertise().equalsIgnoreCase(expertise)) {
                    schedules.add(s);
                }
            });
        });

        return schedules;
    }

    public ArrayList<Schedule> getAvailableAppointmentsByPhysiotherapistName(String physioFullname) {
        ArrayList<Schedule> schedules = new ArrayList<>();

        physiotherapists.forEach(p->{
            p.getTimetable().forEach(s->{
                if(!validation.appointmentIsBookedOrAttended(this.appointments,s.getScheduleId()) && p.getFullName().equalsIgnoreCase(physioFullname)) {
                    schedules.add(s);
                }
            });
        });

        return schedules;
    }

    public Appointment bookAppointment(String scheduleId,String patientId) {
        Schedule schedule = this.getScheduleForBooking(patientId,scheduleId);

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

    public Appointment changeAppointment(String oldBookingId, String patientId, String scheduleId) {
        Schedule schedule = this.getScheduleForBooking(patientId,scheduleId);

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
