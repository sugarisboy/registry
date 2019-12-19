package com.example.scenes;

import com.example.Main;
import com.example.scenes.stages.CreateStage;
import com.example.scenes.stages.EditStage;
import com.example.scenes.stages.FindStage;
import com.example.service.AppService;
import com.example.storage.OwnStore;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.swing.*;

public class AppBar extends MenuBar {

    private static final String ABOUT_MSG =
        "" +
            "Написать программу, работающую с информацией " +
            "\n о служащих учреждения. Данные об отдельном " +
            "\n работнике состоят из имени, фамилии, отчества, " +
            "\n даты рождения, образования, должности и " +
            "\n названия отдела." +
            "\n " +
            "\n Главное окно приложения должно содержать " +
            "\n список людей, отвечающий условиям, которые " +
            "\n определяются в меню." +
            "\n " +
            "\n Главное меню должно " +
            "\n содержать пункты для создания, изменения и " +
            "\n отображения данных: чтение данных из файла, сохранение " +
            "\n данных в файл, редактирование информации о служащем, " +
            "\n добавление информации о служащем, вывод " +
            "\n списка работников с заданным образованием, вывод " +
            "\n списка работников заданного возраста, вывод списка " +
            "\n работников заданного отдела,а также вывод " +
            "\n информации о самойпрограмме(пунктменю «О программе»).";

    private Stage primary;
    private MainScene parent;

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

        MenuItem addWorkerItem = new MenuItem("Создать");
        addWorkerItem.setOnAction(event ->
            new CreateStage(parent)
        );

        MenuItem editWorkerItem = new MenuItem("Изменить");
        editWorkerItem.setOnAction(event ->
            new EditStage(parent)
        );

        MenuItem removeWorkerItem = new MenuItem("Удалить");
        removeWorkerItem.setOnAction(event -> {
            System.out.println("1");
                AppService service = Main.getService();
                parent.getWorkerList().getSelectionModel().getSelectedItems()
                    .forEach(service::removeWorker);
                parent.getWorkerList().refresh();
            }
        );

        menu.getItems().addAll(
            addWorkerItem,
            editWorkerItem,
            removeWorkerItem
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
}
