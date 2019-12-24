package com.example.service;

import com.example.Main;
import com.example.dao.Doctor;
import com.example.dao.WorkDay;
import com.example.dao.WorkTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class AppService {

    public WorkDay getDoctorsByDay(LocalDate date) throws NoSuchElementException {
        return Main.getApp().getStore().getWorkDays().stream()
            .filter(d -> d.getDate().equals(date))
            .findFirst()
            .get();
    }

    public Doctor getDoctorById(Long id) throws NoSuchElementException {
        return Main.getApp().getStore().getDoctors().stream()
            .filter(d -> d.getId() == id)
            .findFirst()
            .get();
    }

    public boolean isFree(LocalDate date, String cabinet, boolean isMorning, Doctor doctor) {
        return getDoctorsByDay(date).getHostDoctors().stream()
            .filter(i -> i.getDoctorId() != doctor.getId() && i.getCabinet().equalsIgnoreCase(cabinet) && isMorning == i.isMorning())
            .count() == 0;
    }

    public boolean canCreateEntry(LocalDate date, Doctor doctor) {
        LocalDate temp = date;
        while (!temp.getDayOfWeek().equals(DayOfWeek.MONDAY))
            temp = temp.minusDays(1);

        int weekWorkDayCount = 0;
        for (int i = 0; i < 7; i++) {
            if (getDoctorsByDay(temp).getHostDoctors().stream()
                    .filter(d -> d.getDoctorId() == doctor.getId())
                    .count() != 0
            ) {
                weekWorkDayCount++;
            }
            temp = temp.plusDays(1);
        }

        boolean freeDay = getDoctorsByDay(date).getHostDoctors().stream()
            .filter(i -> i.getDoctorId() == doctor.getId())
            .count() == 0;

        return freeDay && weekWorkDayCount < 5;
    }

    public void removeEntry(LocalDate date, String cabinet, boolean isMorning) {
        List<WorkTime> hostDoctors = getDoctorsByDay(date).getHostDoctors();
        hostDoctors.removeIf(i -> i.getCabinet().equalsIgnoreCase(cabinet) && i.isMorning() == isMorning);
    }
}
