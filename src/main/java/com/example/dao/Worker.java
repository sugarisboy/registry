package com.example.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

@Data
@Builder
public class Worker {

    @Expose
    @SerializedName("first_name")
    private String firstName;

    @Expose
    @SerializedName("last_name")
    private String lastName;

    @Expose
    @SerializedName("role")
    private String role;

    @Expose
    @SerializedName("department")
    private String department;

    @Expose
    @SerializedName("birthday")
    private LocalDate birthday;

    @Expose
    @SerializedName("education")
    private EducationLevel educationLevel;

    public int getAge() {
        if (birthday == null)
            throw new RuntimeException("Возраст пользователя неопределен.");
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
