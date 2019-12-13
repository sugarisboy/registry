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
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.dao.EducationLevel.NO_SELECT;

public class EditStage extends Stage {

    protected MainScene parent;
    protected Worker worker;

    public EditStage(MainScene parent) {
        this.parent = parent;

        if (isCanOpen()) {
            open();
        } else {
            error();
        }
    }

    private void open() {
        VBox box = new VBox();

        MyTextField fieldName = new MyTextField("Имя", worker.getFirstName());
        MyTextField fieldLastName = new MyTextField("Фамилия", worker.getLastName());
        MyTextField fieldRole = new MyTextField("Должность", worker.getRole());
        MyTextField fieldDepartment = new MyTextField("Отдел", worker.getDepartment());
        MyTextField fieldBirthday = new MyTextField("Дата рождения", worker.getBirthday().toString());

        ObservableList<String> educations = FXCollections.observableList(
            Stream.of(EducationLevel.values())
                .map(EducationLevel::getI18n)
                .collect(Collectors.toList())
        );

        EducationLevel level = worker.getEducationLevel() == null ? NO_SELECT : worker.getEducationLevel();
        MyChoiseBox education = new MyChoiseBox("Образование", educations, level.getI18n());

        Button button = new Button("Сохранить");
        button.setOnAction(event -> {
            onClick(
                fieldName,
                fieldLastName,
                fieldRole,
                fieldDepartment,
                fieldBirthday,
                education
            );
        });

        box.getChildren().addAll(
            fieldName,
            fieldLastName,
            fieldRole,
            fieldDepartment,
            fieldBirthday,
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

    private void error() {
        JOptionPane.showMessageDialog(null,
            "Необходимовы выбрать 1 рабочего.", "Ошибка",
            JOptionPane.ERROR_MESSAGE);
    }

    protected void onClick(
        MyTextField fieldName,
        MyTextField fieldLastName,
        MyTextField fieldRole,
        MyTextField fieldDepartment,
        MyTextField fieldBirthday,
        MyChoiseBox education
    ) {
        worker.setFirstName(fieldName.getValue());
        worker.setLastName(fieldLastName.getValue());
        worker.setRole(fieldRole.getValue());
        worker.setDepartment(fieldDepartment.getValue());

        EducationLevel newEducation = EducationLevel.castFromI18n(education.getValue());
        worker.setEducationLevel(newEducation);

        String[] dates = fieldBirthday.getValue().split("-");
        if (dates.length != 3) {
            JOptionPane.showMessageDialog(null, "Неверый формат даты");
        } else {
            try {
                int year = Integer.valueOf(dates[0]);
                int month = Integer.valueOf(dates[1]);
                int day = Integer.valueOf(dates[2]);

                LocalDate newBirthday = LocalDate.of(year, month, day);
                worker.setBirthday(newBirthday);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Неверый формат даты");
            }
        }

        Main.getService().getOwnStore().save();
        parent.getWorkerList().refresh();
        this.close();
    }

    protected boolean isCanOpen() {
        TableView.TableViewSelectionModel<Worker> selectionModel = parent.getWorkerList().getSelectionModel();
        ObservableList<Worker> selectedItems = selectionModel.getSelectedItems();
        boolean result = selectedItems.size() == 1;
        if (result)
            this.worker = selectedItems.get(0);
        return result;
    }
}
