package com.example.dao;

import lombok.Getter;

public enum  EducationLevel {

    SCHOOL("Среднее общее"),
    COLLEGE("Среднее профессиональное"),
    BACHELOR("Бакалавр"),
    MASTER("Магист");

    @Getter
    private String i18n;

    EducationLevel(String i18n) {
        this.i18n = i18n;
    }
}
