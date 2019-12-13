package com.example.scenes.stages;

import com.example.Main;
import com.example.custom.MyChoiseBox;
import com.example.custom.MyTextField;
import com.example.dao.EducationLevel;
import com.example.dao.Worker;
import com.example.scenes.MainScene;

import javax.swing.*;
import java.time.DateTimeException;
import java.time.LocalDate;

public class CreateStage extends EditStage {

    public CreateStage(MainScene parent) {
        super(parent);
    }

    @Override
    protected boolean isCanOpen() {
        this.worker = Worker.builder()
            .firstName("")
            .lastName("")
            .department("")
            .role("")
            .birthday(LocalDate.of(1970, 1, 1))
            .educationLevel(EducationLevel.NO_SELECT)
            .build();
        return true;
    }

    @Override
    protected void onClick(
        MyTextField fieldName,
        MyTextField fieldLastName,
        MyTextField fieldRole,
        MyTextField fieldDepartment,
        MyTextField fieldBirthday,
        MyChoiseBox educationField
    ) {
        if (isEmpty(fieldName)) {
            JOptionPane.showMessageDialog(null, "Поле имя не должно быть пустым!");
        } else if (isEmpty(fieldLastName)) {
            JOptionPane.showMessageDialog(null, "Поле фамилия не должно быть пустым!");
        } else if (isEmpty(fieldRole)) {
            JOptionPane.showMessageDialog(null, "Поле должность не должно быть пустым!");
        } else if (isEmpty(fieldDepartment)) {
            JOptionPane.showMessageDialog(null, "Поле отдел не должно быть пустым!");
        } else if (isEmpty(fieldBirthday)) {
            JOptionPane.showMessageDialog(null, "Поле день рождения не должно быть пустым!");
        } else {
            LocalDate birthday;

            String[] dates = fieldBirthday.getValue()
                .replace(".", "-")
                .replace(",", "-")
                .replace("/", "-")
                .replace("\\", "-")
                .split("-");

            if (dates.length != 3) {
                JOptionPane.showMessageDialog(null, "Неверый формат даты");
                return;
            } else {
                try {
                    int year = Integer.valueOf(dates[0]);
                    int month = Integer.valueOf(dates[1]);
                    int day = Integer.valueOf(dates[2]);

                    birthday = LocalDate.of(year, month, day);
                    worker.setBirthday(birthday);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Неверый формат даты");
                    return;
                } catch (DateTimeException e) {
                    JOptionPane.showMessageDialog(null, "Неверый формат даты:\n" + e.getMessage());
                    return;
                }
            }

            EducationLevel education = EducationLevel.castFromI18n(educationField.getValue());

            worker.setFirstName(fieldName.getValue());
            worker.setLastName(fieldLastName.getValue());
            worker.setRole(fieldRole.getValue());
            worker.setDepartment(fieldDepartment.getValue());
            worker.setBirthday(birthday);
            worker.setEducationLevel(education);

            Main.getService().addWorker(worker);
            parent.getWorkerList().refresh();
            this.close();
        }
    }

    private boolean isEmpty(StringValue field) {
        return field.getValue().trim().equals("");
    }
}
