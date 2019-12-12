package com.example.scenes;

import com.example.scenes.stages.AddWorkerStage;
import com.example.scenes.stages.EditStage;
import com.example.scenes.stages.FindStage;
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
            menuEdit(),
            menuNavigation(),
            menuHelp()
        );
    }

    private Menu menuEdit() {
        Menu menu = new Menu("Редактирование");

        MenuItem addWorkerItem = new MenuItem("Создать");
        addWorkerItem.setOnAction(event ->
            new AddWorkerStage(primary)
        );

        MenuItem editWorkerItem = new MenuItem("Изменить");
        editWorkerItem.setOnAction(event ->
            new EditStage(parent)
        );

        menu.getItems().addAll(
            addWorkerItem,
            editWorkerItem
        );

        return menu;
    }

    protected Menu menuNavigation() {
        Menu menu = new Menu("Навигация");

        MenuItem findItem = new MenuItem("Найти");
        findItem.setOnAction(event ->
            new FindStage(primary)
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

        menu.getItems().add(aboutItem);
        return menu;
    }
}
