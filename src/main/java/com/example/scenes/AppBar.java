package com.example.scenes;

import com.example.Main;
import com.example.scenes.stages.CreateStage;
import com.example.scenes.stages.EditDoctorStage;
import com.example.scenes.stages.FindStage;
import com.example.storage.OwnStore;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.swing.*;

public class AppBar extends MenuBar {

    private static final String ABOUT_MSG = "Самая лучшая программа.\n\nBy Mihienkhov Alexey!";

    private Stage primary;
    private MainScene parent;
    private Menu sub;

    public AppBar(MainScene parent) {
        this.primary = parent.getPrimary();
        this.parent = parent;
        this.getMenus().addAll(
            menuFile(),
            menuEdit(),
            menuNavigation(),
            menuHelp()
        );
    }

    private Menu menuFile() {
        Menu menu = new Menu("Файл");

        MenuItem addWorkerItem = new MenuItem("Открыть");
        addWorkerItem.setOnAction(event -> {
            Main app = Main.getApp();
            OwnStore ownStore = app.getStoreLoader().loadOwnStore();
            app.setStore(ownStore);
        });

        MenuItem editWorkerItem = new MenuItem("Сохранить");
        editWorkerItem.setOnAction(event -> {
            Main app = Main.getApp();
            app.getStore().save();
        });

        MenuItem removeWorkerItem = new MenuItem("Закрыть");
        removeWorkerItem.setOnAction(event -> {
            Main app = Main.getApp();
            OwnStore ownStore = new OwnStore();
            app.setStore(ownStore);
        });

        menu.getItems().addAll(
            addWorkerItem,
            editWorkerItem,
            removeWorkerItem
        );

        return menu;
    }

    private Menu menuEdit() {
        Menu menu = new Menu("Редактирование");

        MenuItem addDoctorItem = new MenuItem("Создать врача");
        addDoctorItem.setOnAction(event ->
            new CreateStage(parent)
        );

        sub = new Menu("Редактирование врачей");
        Main.getApp().getStore().getDoctors().forEach(doctor -> {
            MenuItem item = new MenuItem(
                doctor.getId() + " " + doctor.getLastName() + " " + doctor.getFirstName() + " " + doctor.getPatronymic());
            item.setOnAction(event -> new EditDoctorStage(parent, doctor));
            sub.getItems().add(item);
        });

        menu.getItems().addAll(
            addDoctorItem,
            sub
        );

        return menu;
    }

    protected Menu menuNavigation() {
        Menu menu = new Menu("Навигация");

        MenuItem findItem = new MenuItem("Найти");
        findItem.setOnAction(event ->
            new FindStage(parent)
        );

        menu.getItems().add(findItem);
        return menu;
    }

    protected Menu menuHelp() {
        Menu menu = new Menu("Помощь");

        MenuItem aboutItem = new MenuItem("О программе");
        aboutItem.setOnAction(event ->
            JOptionPane.showMessageDialog(null, ABOUT_MSG, "О программе", JOptionPane.INFORMATION_MESSAGE)
        );

        MenuItem exitItem = new MenuItem("Выйти");
        aboutItem.setOnAction(event ->
            {
                try {
                    Main.getApp().stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        );

        menu.getItems().addAll(
            aboutItem,
            exitItem
        );
        return menu;
    }

    public void updateSub() {
        sub.getItems().clear();
        Main.getApp().getStore().getDoctors().forEach(doctor -> {
            MenuItem item = new MenuItem(
                doctor.getId() + " " + doctor.getLastName() + " " + doctor.getFirstName() + " " + doctor.getPatronymic());
            item.setOnAction(event -> new EditDoctorStage(parent, doctor));
            sub.getItems().add(item);
        });
    }
}
