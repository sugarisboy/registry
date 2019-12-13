package com.example.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

import static com.example.dao.EducationLevel.NO_SELECT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private EducationLevel educationLevel = NO_SELECT;

    public int getAge() {
        if (birthday == null)
            throw new RuntimeException("Возраст пользователя неопределен.");
        return Period.between(birthday, LocalDate.now()).getYears();
    }
}
