package com.example.custom;

import com.example.scenes.stages.StringValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class MyTextField extends VBox implements StringValue {

    private TextField field;

    public MyTextField(String description) {
        this(description, "");
    }

    public MyTextField(String description, String value) {
        Label label = new Label(description);
        field = new TextField(value);
        this.setPadding(new Insets(8, 8, 8, 8));
        this.getChildren().addAll(label, field);
    }

    public String getValue() {
        return field.getText();
    }
}
