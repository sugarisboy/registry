package com.example.scenes.stages;

import com.example.Main;
import com.example.custom.MyChoiseBox;
import com.example.custom.MyTextField;
import com.example.dao.EducationLevel;
import com.example.dao.Worker;
import com.example.scenes.MainScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.dao.EducationLevel.NO_SELECT;

public class FindStage extends Stage {

    protected MainScene parent;
    protected Worker worker;

    public FindStage(MainScene parent) {
        this.parent = parent;
        open();
    }

    private void open() {
        VBox box = new VBox();

        MyTextField fieldAge = new MyTextField("Возраст", "");
        MyTextField fieldDepartment = new MyTextField("Отдел", "");

        ObservableList<String> educations = FXCollections.observableList(
            Stream.of(EducationLevel.values())
                .map(EducationLevel::getI18n)
                .collect(Collectors.toList())
        );
        MyChoiseBox education = new MyChoiseBox("Образование", educations, NO_SELECT.getI18n());

        Button button = new Button("Сохранить");
        button.setOnAction(event -> {
            onClick(
                fieldAge,
                fieldDepartment,
                education
            );
        });

        box.getChildren().addAll(
            fieldAge,
            fieldDepartment,
            education,
            button
        );

        Scene editScene = new Scene(box, 230, 400);

        this.setTitle("Редактирование");
        this.setScene(editScene);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(parent.getPrimary());
        this.setX(parent.getPrimary().getX() + 200);
        this.setY(parent.getPrimary().getY() + 100);
        this.show();
    }

    protected void onClick(
        MyTextField fieldAge,
        MyTextField fieldDepartment,
        MyChoiseBox education
    ) {
        int age = -1;
        if (!fieldAge.getValue().trim().equals("")) {
            try {
                age = Integer.valueOf(fieldAge.getValue());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Неверный формат возраста!");
                return;
            }
        }

        String department = fieldDepartment.getValue();
        EducationLevel level = EducationLevel.castFromI18n(education.getValue());

        List<Worker> collect = Main.getService().findByFilter(level, department, age);
        ObservableList<Worker> workers = FXCollections.observableList(collect);

        parent.getWorkerList().setItems(workers);
        this.close();
    }
}
