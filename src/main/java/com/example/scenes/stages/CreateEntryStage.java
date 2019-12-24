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
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateEntryStage extends Stage {
    private VBox box;
    private MainScene parent;
    private WorkDay day;

    public CreateEntryStage(MainScene parent, WorkDay day) {
        this.parent = parent;
        this.day = day;

        box = new VBox();

        Scene editScene = new Scene(box, 400, 220);
        this.setTitle(day.getDate().toString() + ": Создание времени приема.");
        this.setScene(editScene);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(parent.getPrimary());
        this.setX(parent.getPrimary().getX() + 200);
        this.setY(parent.getPrimary().getY() + 100);
        this.show();

        open();
    }

    public void open() {

        MyTextField cabinetField = new MyTextField("Кабинет", "");
        MyChoiseBox morningBox = new MyChoiseBox(
            "Время приема",
            FXCollections.observableList(Arrays.asList("Утро", "Вечер")),
            "Утро"
        );

        ObservableList<String> doctorsValue = FXCollections.observableList(
            Main.getApp().getStore().getDoctors().stream()
                .map(d -> d.getId() + " " + d.getLastName() + " " + d.getFirstName() + " " + d.getPatronymic())
                .collect(Collectors.toList())
        );

        MyChoiseBox doctorBox = new MyChoiseBox(
            "Время приема",
            FXCollections.observableList(doctorsValue),
            doctorsValue.get(0)
        );

        AppService service = Main.getService();

        Button buttonEdit = new Button("Сохранить");
        buttonEdit.setOnMouseClicked(event -> {
            String cabinet = cabinetField.getValue();
            boolean isMorning = morningBox.getValue().equalsIgnoreCase("Утро");

            String doctorValue = doctorBox.getValue();
            Long id = Long.valueOf(doctorValue.split(" ")[0]);
            Doctor doctor = service.getDoctorById(id);

            if (!service.canCreateEntry(day.getDate(), doctor)) {
                JOptionPane.showMessageDialog(null, "Невозможно создать запись:\n" +
                    "\n" +
                    "Возможные причины:\n" +
                    "\n" +
                    "1. Врач уже принимает 5 раз на неделе, это предельное число.\n" +
                    "\n" +
                    "2. Врач принимает в этот день, но в другое время."
                );
                return;
            }

            if (service.isFree(day.getDate(), cabinet, isMorning, doctor)) {
                WorkTime workTime = new WorkTime();
                workTime.setCabinet(cabinet);
                workTime.setMorning(isMorning);
                workTime.setDoctorId(id);
                day.getHostDoctors().add(workTime);

                parent.displayCalendar(Main.getApp().getStore());
                this.close();
            } else {
                JOptionPane.showMessageDialog(null, "Это время занято");
            }
        });

        box.getChildren().addAll(
            cabinetField,
            morningBox,
            doctorBox,
            buttonEdit
        );
    }
}
