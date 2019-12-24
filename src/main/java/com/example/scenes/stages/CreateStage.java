package com.example.scenes.stages;

import com.example.Main;
import com.example.custom.MyTextField;
import com.example.custom.StringValue;
import com.example.dao.Doctor;
import com.example.scenes.MainScene;
import com.example.storage.OwnStore;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.OptionalLong;


public class CreateStage extends Stage {

    private MainScene parent;
    private VBox box;

    public CreateStage(MainScene parent) {
        this.parent = parent;

        box = new VBox();

        Scene editScene = new Scene(box, 400, 300);
        this.setTitle("Добавление нового врача");
        this.setScene(editScene);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(parent.getPrimary());
        this.setX(parent.getPrimary().getX() + 200);
        this.setY(parent.getPrimary().getY() + 100);
        this.show();

        open();
    }

    private void open() {
        MyTextField name1Field = new MyTextField("Имя", "");
        MyTextField name2Field = new MyTextField("Фамилия", "");
        MyTextField name3Field = new MyTextField("Отчество", "");
        MyTextField roleField = new MyTextField("Специальность", "");

        Button ok = new Button("Сохранить");
        ok.setOnMouseClicked(event -> {
            if (isEmpty(name1Field) || isEmpty(name2Field) || isEmpty(name3Field) || isEmpty(roleField)) {
                JOptionPane.showMessageDialog(null, "Введите все данные.");
                return;
            }

            OwnStore store = Main.getApp().getStore();
            OptionalLong maxId = store.getDoctors().stream().mapToLong(Doctor::getId).max();
            long id = maxId.isPresent() ? maxId.getAsLong() : 1;

            Doctor doctor = new Doctor();
            doctor.setId(id);
            doctor.setFirstName(name1Field.getValue());
            doctor.setLastName(name2Field.getValue());
            doctor.setPatronymic(name3Field.getValue());
            doctor.setRole(roleField.getValue());

            store.getDoctors().add(doctor);
            Main.getApp().setStore(Main.getApp().getStore());
            this.close();
        });

        box.getChildren().addAll(
            name1Field,
            name2Field,
            name3Field,
            roleField,
            ok
        );
    }


    private boolean isEmpty(StringValue field) {
        return field.getValue().trim().equals("");
    }
}
