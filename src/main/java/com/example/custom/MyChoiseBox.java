package com.example.custom;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class MyChoiseBox extends VBox {

    private ChoiceBox<String> box;

    public MyChoiseBox(String description, ObservableList<String> variants, String value) {
        Label label = new Label(description);
        box = new ChoiceBox<>(variants);
        box.setValue(value);
        this.setPadding(new Insets(8, 8, 8, 8));
        this.getChildren().addAll(label, box);
    }

    public String getValue() {
        return box.getValue();
    }
}
