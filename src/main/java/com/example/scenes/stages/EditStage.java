package com.example.scenes.stages;

import com.example.Main;
import com.example.custom.MyChoiseBox;
import com.example.custom.MyTextField;
import com.example.dao.Doctor;
import com.example.dao.WorkDay;
import com.example.dao.WorkTime;
import com.example.scenes.MainScene;
import com.example.service.AppService;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Arrays;
import java.util.Optional;

public class EditStage extends Stage {

    private VBox box;
    private MainScene parent;
    private WorkDay day;
    private Doctor doctor;

    public EditStage(MainScene parent, WorkDay day, Doctor doctor) {
        this.parent = parent;
        this.day = day;
        this.doctor = doctor;

        box = new VBox();

        Scene editScene = new Scene(box, 400, 220);
        this.setTitle(day.getDate().toString() + ": " + doctor.getLastName());
        this.setScene(editScene);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(parent.getPrimary());
        this.setX(parent.getPrimary().getX() + 200);
        this.setY(parent.getPrimary().getY() + 100);
        this.show();

        open();
    }

    public void open() {
        AppService service = Main.getService();
        Optional<WorkTime> optionalWorkTIme = service.getDoctorsByDay(day.getDate()).getHostDoctors().stream()
            .filter(i -> i.getDoctorId() == doctor.getId())
            .findFirst();
        if (!optionalWorkTIme.isPresent()) {
            JOptionPane.showMessageDialog(null, "Неизвестная ошибка");
        }

        WorkTime workTime = optionalWorkTIme.get();

        String cabinet = workTime.getCabinet();
        boolean isMorning = workTime.isMorning();

        MyTextField cabinetField = new MyTextField("Кабинет", cabinet);
        MyChoiseBox morningBox = new MyChoiseBox(
            "Время приема",
            FXCollections.observableList(Arrays.asList("Утро", "Вечер")),
            isMorning ? "Утро" : "Вечер"
        );

        Button buttonEdit = new Button("Сохранить");
        buttonEdit.setOnMouseClicked(event -> {
            String _cabinet = cabinetField.getValue();
            boolean _isMorning = morningBox.getValue().equalsIgnoreCase("Утро");
            if (service.isFree(day.getDate(), _cabinet, _isMorning, doctor)) {
                workTime.setCabinet(_cabinet);
                workTime.setMorning(_isMorning);
                parent.displayCalendar(Main.getApp().getStore());
                this.close();
            } else {
                JOptionPane.showMessageDialog(null, "Это время занято");
            }
        });

        Button buttonDelete = new Button("Удалить запись");
        buttonDelete.setOnMouseClicked(event -> {
            service.removeEntry(day.getDate(), cabinet, isMorning);
            parent.displayCalendar(Main.getApp().getStore());
            this.close();
        });

        box.getChildren().addAll(
            cabinetField,
            morningBox,
            buttonEdit,
            buttonDelete
        );
    }
}
