package com.bpc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Physiotherapist extends Personnel{
    private final ArrayList<Treatment> treatments = new ArrayList<>();
    private final ArrayList<Schedule> timetable = new ArrayList<>();

    Physiotherapist(String fullName, String address, String phone,int totalEverNumOfPhysios) {
        super(fullName, address, phone, "physiotherapist", totalEverNumOfPhysios);

        //load timetable
        String filePath = "src/main/java/com/bpc/mock-data.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("#-")) {
                    String physioId = line.split("#-")[1].trim().split("/")[0].trim();
                    if(physioId.equals(this.getId())){
                        addSchedule(getScheduleFromTxt(line));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Treatment> getTreatments() {
        return treatments;
    }

    public void addTreatment(String treatmentName, String expertiseName) {
        this.treatments.add(new Treatment(treatmentName, expertiseName));
    }

    public ArrayList<Schedule> getTimetable() {
        return timetable;
    }

    private void addSchedule(Schedule schedule) {
        this.timetable.add(schedule);
    }

    private Schedule getScheduleFromTxt(String line) {
        String [] infoList = line.split("#-")[1].trim().split("/");
        Treatment treatment = new Treatment(infoList[2].trim(),infoList[1].trim());
        String dateTime = infoList[3].trim();

        return new Schedule(dateTime, this, treatment);
    }
}
