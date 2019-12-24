package com.example.storage;

import com.example.dao.Doctor;
import com.example.dao.WorkDay;
import com.example.storage.store.AppStore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OwnStore extends AppStore {

    @Expose
    @SerializedName("doctors")
    private List<Doctor> doctors = new ArrayList<>();

    @Expose
    @SerializedName("days")
    private List<WorkDay> workDays = new ArrayList<>();
}
