package com.example.scenes.stages;

import com.example.scenes.CalendarList;
import com.example.scenes.MainScene;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SearchResultStage extends Stage {

    public SearchResultStage(MainScene parent, CalendarList calendarList) {
        Pane pane = new Pane();
        Scene editScene = new Scene(pane, 1280, 720);
        this.setTitle("Результаты поиска!");

        this.setScene(editScene);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(parent.getPrimary());
        this.setX(parent.getPrimary().getX() + 200);
        this.setY(parent.getPrimary().getY() + 100);
        this.show();

        pane.getChildren().add(calendarList);
    }
}
