package com.bpc;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    ClinicData clinicData = new ClinicData();

    public void startApp() {
        while (true) {
            System.out.println("--- Main Menu ---\n");
            System.out.println("Press 1 to: Add a patient\n");
            System.out.println("Press 2 to: Remove a patient\n");
            System.out.println("Press 3 to: View patients\n");
            System.out.println("Press 4 to: Search and book an appointment\n");
            System.out.println("Press 5 to: Cancel an appointment\n");
            System.out.println("Press 6 to: Change an appointment");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    addPatientMenu();
                    break;
                case "2":
                    removePatientMenu();
                    break;
                case "3":
                    viewPatientsMenu();
                    break;
                case "4":
                    searchAndBookAppointmentMenu();
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }

    private void addPatientMenu() {
        boolean inMenu = true;

        while (inMenu) {
            boolean isRunning = true;

            while (isRunning) {
                String fullName, address, phone;

                System.out.println("Enter patient full name:");
                fullName = scanner.nextLine();
                System.out.println("Enter patient address:");
                address = scanner.nextLine();
                System.out.println("Enter patient phone:");
                phone = scanner.nextLine();

                try {
                    Patient patient = clinicData.addPatient(fullName, address, phone);
                    System.out.println("\nPatient has been added, patient id: " + patient.getId() + "\n");
                    isRunning = false;
                } catch (Exception e) {
                    System.out.println("\n" + e.getMessage() + ", try again\n");
                }
            }
            System.out.println("Press 1: Add another patient\n");
            System.out.println("Press 2: Go to Main Menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }

    private void viewPatientsMenu() {
        System.out.println("\nPatient ID" + "\t" + "Full name" + "\t" + "Address" + "\t" + "Phone Number" + "\n");
        clinicData.getPatients().forEach(patient -> {
            System.out.println(patient.getId() + "\t" + patient.getFullName() + "\t" + patient.getAddress() + "\t" + patient.getPhone() + "\n");
        });

        while (true) {
            System.out.println("Press 0: Go to Main Menu\n");

            String input = scanner.nextLine();

            if (input.equals("0")) {
                return;
            }
            System.out.println("Invalid input, please try again");
        }
    }

    private void removePatientMenu() {
        boolean inMenu = true;

        while (inMenu) {
            boolean isRunning = true;

            while (isRunning) {
                System.out.println("Enter patient ID:\n");
                String input = scanner.nextLine();

                try {
                    clinicData.removePatient(input);
                    System.out.println("Patient has been removed\n");
                    isRunning = false;
                } catch (Exception e) {
                    System.out.println("\n" + e.getMessage() + ", try again\n");
                }
            }


            System.out.println("Press 1: Remove another patient\n");
            System.out.println("Press 2: Go to Main Menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }

    private void searchAndBookAppointmentMenu() {
        boolean inMenu = true;

        while (inMenu) {
            boolean running = true;
            while (running) {
                System.out.println("Press 0 to: Go to main menu\n");
                System.out.println("Press 1 to: Search for available appointments by expertise\n");
                System.out.println("Press 2 to: Search for available appointments by physiotherapist name\n");

                String input = scanner.nextLine();

                switch (input) {
                    case "0":
                        return;
                    case "1":
                        System.out.println("Enter expertise name: ");
                        String expertiseName = scanner.nextLine();

                        ArrayList<Schedule> schedules = clinicData.getAvailableAppointmentsByExpertise(expertiseName);
                        if (schedules.isEmpty()) {
                            System.out.println("No appointments found for expertise name: " + expertiseName);
                            break;
                        }

                        schedules.forEach(s -> {
                            Physiotherapist physio = clinicData.getPhysiotherapist(s.getPhysioId());
                            System.out.println(s.getScheduleId() + "\t" + s.getDateTime() + "\t" + physio.getFullName() + "\t" + s.getTreatment().getName() + "\t" + s.getTreatment().getExpertise());
                        });
                        running = false;
                        break;
                    case "2":
                        System.out.println("Enter physiotherapist name: ");
                        String physioName = scanner.nextLine();

                        ArrayList<Schedule> schedules2 = clinicData.getAvailableAppointmentsByPhysiotherapistName(physioName);
                        if (schedules2.isEmpty()) {
                            System.out.println("No appointments found for physiotherapist: " + physioName);
                            break;
                        }

                        schedules2.forEach(s -> {
                            Physiotherapist physio = clinicData.getPhysiotherapist(s.getPhysioId());
                            System.out.println(s.getScheduleId() + "\t" + s.getDateTime() + "\t" + physio.getFullName() + "\t" + s.getTreatment().getName() + "\t" + s.getTreatment().getExpertise());
                        });
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid input, please try again");
                }
            }


            System.out.println("Press 0 to: Go to main menu\n");
            System.out.println("Press 1 to: Book an appointment\n");
            System.out.println("Press 2 to: Search for available appointments again\n");

            String input = scanner.nextLine();

            switch (input) {
                case "0":
                    inMenu = false;
                    break;
                case "1":
                    bookAppointmentMenu();
                    break;
                case "2":
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }

    public void bookAppointmentMenu() {
        boolean inMenu = true;
        while (inMenu) {
            boolean running = true;
            while (running) {
                System.out.println("Enter patient ID:\n");
                String patientId = scanner.nextLine();
                System.out.println("Enter schedule ID:\n");
                String scheduleId = scanner.nextLine();

                try {
                    Appointment apt = clinicData.bookAppointment(scheduleId, patientId);
                    System.out.println("Appointment has been booked, booking id: " + apt.getBookingId());
                    running = false;
                } catch (Exception e) {
                    System.out.println("\n" + e.getMessage() + ", try again\n");
                }
            }

            System.out.println("Press 1 to: Book another appointment\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    break;
                case "2":
                    inMenu = false;
                    break;
            }
        }
    }
}
