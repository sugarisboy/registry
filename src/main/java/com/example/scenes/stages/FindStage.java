package com.example.scenes.stages;

import com.example.Main;
import com.example.custom.MyChoiseBox;
import com.example.dao.Doctor;
import com.example.scenes.CalendarList;
import com.example.scenes.MainScene;
import com.example.storage.OwnStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class FindStage extends Stage {

    private MainScene parent;
    private VBox box;

    public FindStage(MainScene parent) {
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
        OwnStore store = Main.getApp().getStore();
        Set<String> collect = store.getDoctors().stream()
            .map(Doctor::getRole)
            .collect(Collectors.toSet());
        ObservableList<String> roles = FXCollections.observableList(new ArrayList<>(collect));
        if (roles.size() == 0) {
            JOptionPane.showMessageDialog(null, "Специалисты не найдены!");
            return;
        }

        MyChoiseBox choiseBox = new MyChoiseBox("Специальность", roles, roles.get(0));

        Button button = new Button("Найти");
        button.setOnMouseClicked(event -> {
            CalendarList calendar = new CalendarList(parent, choiseBox.getValue());
            new SearchResultStage(parent, calendar);
            this.close();
        });

        box.getChildren().addAll(choiseBox, button);
    }
}
