package com.bpc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private String dateTime;
    private Physiotherapist physiotherapist;
    private Treatment treatment;

    public Schedule(String dateTime, Physiotherapist physiotherapist, Treatment treatment) {
        this.dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTime));
        this.physiotherapist = physiotherapist;
        this.treatment = treatment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Physiotherapist getPhysiotherapist() {
        return physiotherapist;
    }

    public Treatment getTreatment() {
        return treatment;
    }
}
