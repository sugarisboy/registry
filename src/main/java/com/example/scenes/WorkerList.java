package com.example.scenes;

import com.example.Main;
import com.example.dao.EducationLevel;
import com.example.dao.Worker;
import com.example.storage.OwnStore;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class WorkerList extends TableView<Worker> {

    public WorkerList(MainScene parent) {
        OwnStore store = Main.getStore();
        ObservableList<Worker> workers = FXCollections.observableList(store.getWorkers());
        this.setItems(workers);
        this.getColumns().addAll(
            columnName(),
            columnLastName(),
            columnRole(),
            columnDepartment(),
            columnEducation(),
            columnBirthday()
        );
    }

    protected TableColumn columnName() {
        TableColumn<Worker, String> column = new TableColumn<>("Имя");
        column.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        return column;
    }

    protected TableColumn columnLastName() {
        TableColumn<Worker, String> column = new TableColumn<>("Фамилия");
        column.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        return column;
    }

    protected TableColumn columnRole() {
        TableColumn<Worker, String> column = new TableColumn<>("Должность");
        column.setCellValueFactory(new PropertyValueFactory<>("role"));
        return column;
    }

    protected TableColumn columnDepartment() {
        TableColumn<Worker, String> column = new TableColumn<>("Отдел");
        column.setCellValueFactory(new PropertyValueFactory<>("department"));
        return column;
    }

    protected TableColumn columnBirthday() {
        TableColumn<Worker, LocalDate> column = new TableColumn<>("День рождения");
        column.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        return column;
    }

    protected TableColumn columnEducation() {
        TableColumn<Worker, String> column = new TableColumn<>("Образование");
        column.setCellValueFactory(data -> {
            Worker worker = data.getValue();
            EducationLevel education = worker.getEducationLevel() == null ?
                EducationLevel.NO_SELECT : worker.getEducationLevel();

            return new SimpleStringProperty(education.getI18n());
        });
        return column;
    }
}
