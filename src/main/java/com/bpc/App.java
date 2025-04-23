package com.bpc;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
    Scanner scanner = new Scanner(System.in);
    ClinicData clinicData = new ClinicData();
    Utils utils = new Utils();

    private void printSchedules(ArrayList<Schedule> schedules) {
        schedules.forEach(s -> {
            Physiotherapist physio = clinicData.getPhysiotherapist(s.getPhysioId());
            System.out.println("\n--- Schedule ID: " + s.getScheduleId() + " ---\n" +
                    "Physiotherapist: " + physio.getFullName() + " | " +
                    "Date: " + utils.formatToDisplayDate(s.getDateTime()) + " | " +
                    "Treatment: " + s.getTreatment().getName() + " (" + s.getTreatment().getExpertise() + ")");
        });
    }

    private void printAppointments(ArrayList<Appointment> appointments) {
        appointments.forEach(a -> {
            Physiotherapist physio = clinicData.getPhysiotherapist(a.getSchedule().getPhysioId());
            Patient pat = clinicData.getPatient(a.getPatientId());
            System.out.println("\n--- Booking ID: " + a.getBookingId() + " ---\n" +
                    "Physiotherapist: " + physio.getFullName() + " | " +
                    "Patient: " + pat.getFullName() + " | " +
                    "Date: " + utils.formatToDisplayDate(a.getSchedule().getDateTime()) + " | " +
                    "Treatment: " + a.getSchedule().getTreatment().getName() + " (" + a.getSchedule().getTreatment().getExpertise() + ")");
        });
    }

    public void startApp() {
        while (true) {
            System.out.println("--- Main Menu ---\n");
            System.out.println("Press 1 to: Add a patient\n");
            System.out.println("Press 2 to: Remove a patient\n");
            System.out.println("Press 3 to: View patients\n");
            System.out.println("Press 4 to: Search and book an available appointment\n");
            System.out.println("Press 5 to: Search and cancel/change/attend a booked appointment\n");

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
                case "5":
                    searchAndCancelOrChangeAppointment();
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
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Press 0 to: Go to main menu\n");
                System.out.println("Press 1 to: Search for available appointments by expertise\n");
                System.out.println("Press 2 to: Search for available appointments by physiotherapist name\n");
                System.out.println("Press 3 to: Book an available appointment\n");

                String input = scanner.nextLine();

                switch (input) {
                    case "0":
                        return;
                    case "1":
                        searchByExpertiseMenu();
                        break;
                    case "2":
                        searchByPhysiotherapistNameMenu();
                        break;
                    case "3":
                        bookAppointmentMenu();
                        break;
                    default:
                        System.out.println("Invalid input, please try again");
                }
            }
        }
    }

    private void searchByExpertiseMenu() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Enter expertise name: ");
                String expertiseName = scanner.nextLine();

                ArrayList<Schedule> schedules = clinicData.getAvailableAppointmentsByExpertise(expertiseName);
                if (schedules.isEmpty()) {
                    System.out.println("No available appointments found for expertise name: " + expertiseName);
                } else {
                    printSchedules(schedules);
                }
                running = false;
            }

            System.out.println("\nPress 1 to: Search by expertise again\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    running = true;
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }

    private void searchByPhysiotherapistNameMenu() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Enter physiotherapist name: ");
                String physioName = scanner.nextLine();

                ArrayList<Schedule> schedules = clinicData.getAvailableAppointmentsByPhysiotherapistName(physioName);
                if (schedules.isEmpty()) {
                    System.out.println("No available appointments found for physiotherapist: " + physioName);
                } else {
                    printSchedules(schedules);
                }
                running = false;
            }

            System.out.println("\nPress 1 to: Search by physiotherapist name again\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    running = true;
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }

    private void searchAndCancelOrChangeAppointment() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Press 0 to: Go to main menu\n");
                System.out.println("Press 1 to: Search for booked appointments by expertise\n");
                System.out.println("Press 2 to: Search for booked appointments by physiotherapist name\n");
                System.out.println("Press 3 to: Search for booked appointments by patient ID\n");
                System.out.println("Press 4 to: Cancel a booked appointment\n");
                System.out.println("Press 5 to: Change a booked appointment\n");
                System.out.println("Press 6 to: Attend a booked appointment\n");

                String input = scanner.nextLine();

                switch (input) {
                    case "0":
                        return;
                    case "1":
                        searchBookedAppointmentsByExpertiseMenu();
                        break;
                    case "2":
                        searchBookedAppointmentsByPhysiotherapistNameMenu();
                        break;
                    case "3":
                        searchBookedAppointmentsByPatientIdMenu();
                        break;
                    case "4":
                        cancelAppointmentMenu();
                        break;
                    case "5":
                        changeAppointmentMenu();
                        break;
                    case "6":
                        attendAppointmentMenu();
                        break;
                    default:
                        System.out.println("Invalid input, please try again");
                }
            }
        }
    }

    private void searchBookedAppointmentsByExpertiseMenu() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Enter expertise name: ");
                String expertiseName = scanner.nextLine();

                ArrayList<Appointment> appointments = clinicData.getBookedAppointmentsByExpertise(expertiseName);
                if (appointments.isEmpty()) {
                    System.out.println("No booked appointments found for expertise name: " + expertiseName);
                } else {
                    printAppointments(appointments);
                }
                running = false;
            }

            System.out.println("\nPress 1 to: Search booked appointments by expertise again\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    running = true;
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }

    private void searchBookedAppointmentsByPhysiotherapistNameMenu() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Enter physiotherapist name: ");
                String physioName = scanner.nextLine();

                ArrayList<Appointment> appointments = clinicData.getBookedAppointmentsByPhysiotherapistName(physioName);
                if (appointments.isEmpty()) {
                    System.out.println("No booked appointments found for physiotherapist name: " + physioName);
                } else {
                    printAppointments(appointments);
                }
                running = false;
            }

            System.out.println("\nPress 1 to: Search booked appointments by physiotherapist name again\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    running = true;
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }

    private void searchBookedAppointmentsByPatientIdMenu() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Enter patient ID: ");
                String patientId = scanner.nextLine();

                ArrayList<Appointment> appointments = clinicData.getBookedAppointmentsByPatientId(patientId);
                if (appointments.isEmpty()) {
                    System.out.println("No booked appointments found for patient ID: " + patientId);
                } else {
                    printAppointments(appointments);
                }
                running = false;
            }

            System.out.println("\nPress 1 to: Search booked appointments by patient ID again\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    running = true;
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }

    private void bookAppointmentMenu() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
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
                    running = false;
                }
            }

            System.out.println("\nPress 1 to: Book an appointment\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    running = true;
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
    }


    private void cancelAppointmentMenu() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Enter booking ID of appointment:\n");
                String bookingId = scanner.nextLine();
                System.out.println("Enter patient ID of appointment patient:\n");
                String patientId = scanner.nextLine();

                try {
                    clinicData.cancelAppointment(bookingId, patientId);
                    System.out.println("Appointment has been cancelled, booking id: " + bookingId);
                    running = false;
                } catch (Exception e) {
                    System.out.println("\n" + e.getMessage() + ", try again\n");
                    running = false;
                }
            }

            System.out.println("\nPress 1 to: Cancel an appointment\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    running = true;
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }

        }
    }

    private void changeAppointmentMenu() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Enter booking ID of appointment to change from:\n");
                String oldBookingId = scanner.nextLine();
                System.out.println("Enter schedule ID of appointment to change to:\n");
                String scheduleId = scanner.nextLine();
                System.out.println("Enter patient ID of appointment patient:\n");
                String patientId = scanner.nextLine();

                try {
                    Appointment apt = clinicData.changeAppointment(oldBookingId, patientId, scheduleId);
                    System.out.println("Appointment with booking id: " + oldBookingId + " has been cancelled, " + "\nwhile new appointment with booking id: " + apt.getBookingId() + " has been booked");
                    running = false;
                } catch (Exception e) {
                    System.out.println("\n" + e.getMessage() + ", try again\n");
                    running = false;
                }
            }

            System.out.println("\nPress 1 to: Change an appointment\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    running = true;
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }

        }
    }

    private void attendAppointmentMenu() {
        boolean inMenu = true;
        boolean running = true;

        while (inMenu) {
            while (running) {
                System.out.println("Enter booking ID of appointment:\n");
                String bookingId = scanner.nextLine();
                System.out.println("Enter patient ID of appointment patient:\n");
                String patientId = scanner.nextLine();

                try {
                    clinicData.attendAppointment(bookingId, patientId);
                    System.out.println("Appointment has been attended, booking id: " + bookingId);
                    running = false;
                } catch (Exception e) {
                    System.out.println("\n" + e.getMessage() + ", try again\n");
                    running = false;
                }
            }

            System.out.println("\nPress 1 to: Attend another appointment\n");
            System.out.println("Press 2 to: Go to previous menu\n");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    running = true;
                    break;
                case "2":
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }

        }
    }
}
