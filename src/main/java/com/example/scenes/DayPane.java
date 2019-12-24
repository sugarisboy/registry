package com.example.scenes;

import com.example.Main;
import com.example.dao.Doctor;
import com.example.dao.WorkDay;
import com.example.scenes.stages.CalendarClickStage;
import com.example.service.AppService;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.NoSuchElementException;

public class DayPane extends VBox {

    public DayPane(MainScene parent, LocalDate date, String role) {
        this.setPadding(new Insets(0, 4, 0, 4));

        Label dayLabel = new Label(
            date.getDayOfMonth() + " " + date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        );

        Font font = new Font("Roboto", 14);
        dayLabel.setTextFill(Paint.valueOf("#525252"));
        dayLabel.setFont(font);

        this.getChildren().add(dayLabel);

        AppService service = Main.getService();

        if (date.equals(LocalDate.now())) {
            this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#a1ffaf"), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (date.getMonth() != LocalDate.now().getMonth()) {
            this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#f0f0f0"), CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
        }

        if (role == null)
            this.setCursor(Cursor.HAND);

        WorkDay workDay;
        try {
            workDay = service.getDoctorsByDay(date);
            if (role == null)
                this.setOnMouseClicked(event -> new CalendarClickStage(parent, workDay));
            if (workDay.getHostDoctors().size() == 0) {
                Label doctorEntry = new Label("Приемов нет");
                this.getChildren().add(doctorEntry);
            }

        } catch (NoSuchElementException e) {
            Label doctorEntry = new Label("Приемов нет");
            this.getChildren().add(doctorEntry);

            WorkDay workDay1 = new WorkDay(date);
            Main.getApp().getStore().getWorkDays().add(workDay1);
            if (role == null)
                this.setOnMouseClicked(event -> new CalendarClickStage(parent, workDay1));

            return;
        }

        workDay.getHostDoctors().stream().forEach(host -> {
            try {
                Doctor doctor = service.getDoctorById(host.getDoctorId());
                if (role == null || doctor.getRole().equalsIgnoreCase(role)) {

                    String name = String.format("%s %.1s. %.1s.",
                        doctor.getLastName(), doctor.getFirstName(), doctor.getPatronymic()
                    );
                    String cabinet = host.getCabinet();
                    boolean isMorning = host.isMorning();

                    String title = String.format("%s   %s %s",
                        name,
                        cabinet,
                        isMorning ? "Утро" : "Вечер"
                    );

                    Label doctorEntry = new Label(title);
                    this.getChildren().add(doctorEntry);
                }
            } catch (NoSuchElementException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Неудалось обработать врача с ID " + host.getDoctorId() + " на " + workDay.getDate(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
