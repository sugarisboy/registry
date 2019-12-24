package com.example.scenes;

import com.example.storage.OwnStore;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;

public class MainScene extends Scene {

    @Getter
    private Stage primary;
    @Getter
    private BorderPane pane;
    @Getter
    private AppBar appBar;
    @Getter
    private CalendarList calendarList;

    public MainScene(Stage primary) {
        super(new BorderPane(), 1280, 720);
        this.primary = primary;

        pane = (BorderPane) this.getRoot();
        pane.setCenter(new Label("Файл -> Открыть"));
        pane.setTop(appBar = new AppBar(this));

        this.setRoot(pane);
    }

    public void displayCalendar(OwnStore store) {
        pane.setCenter(calendarList = new CalendarList(this));
    }
}
