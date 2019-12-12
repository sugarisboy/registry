package com.example.scenes;

import javafx.scene.Scene;
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
    private WorkerList workerList;

    public MainScene(Stage primary) {
        super(new BorderPane(), 1280, 720);
        this.primary = primary;

        pane = (BorderPane) this.getRoot();
        pane.setTop(appBar = new AppBar(this));
        pane.setCenter(workerList = new WorkerList(this));

        this.setRoot(pane);
    }
}
