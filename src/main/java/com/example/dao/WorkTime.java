package com.example.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WorkTime {

    @Expose
    @SerializedName("doctor_id")
    private Long doctorId;

    @Expose
    @SerializedName("morning")
    private boolean morning;

    @Expose
    @SerializedName("cabinet")
    private String cabinet;

}
