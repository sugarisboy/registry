package com.example.dao;

import lombok.Getter;

public enum  EducationLevel {

    NO_SELECT("Не указано"),
    SCHOOL("Среднее общее"),
    COLLEGE("Среднее профессиональное"),
    BACHELOR("Бакалавр"),
    MASTER("Магист");

    @Getter
    private String i18n;

    EducationLevel(String i18n) {
        this.i18n = i18n;
    }

    public static EducationLevel castFromI18n(String i18n) {
        for (EducationLevel value : values()) {
            if (value.getI18n().equalsIgnoreCase(i18n))
                return value;
        }
        return NO_SELECT;
    }
}
