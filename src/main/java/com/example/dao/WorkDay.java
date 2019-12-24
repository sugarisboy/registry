package com.example.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class WorkDay {

    public WorkDay(LocalDate date) {
        this.date = date;
    }

    @Expose
    @SerializedName("date")
    private LocalDate date;

    @Expose
    @SerializedName("work_time")
    private List<WorkTime> hostDoctors = new ArrayList<>();
}
