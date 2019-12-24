package com.example.scenes.stages;

import com.example.Main;
import com.example.dao.WorkDay;
import com.example.scenes.MainScene;
import com.example.service.AppService;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.NoSuchElementException;

public class CalendarClickStage extends Stage {

    public CalendarClickStage(MainScene parent, WorkDay day) {
        VBox box = new VBox();

        Scene editScene = new Scene(box, 300, 500);
        this.setTitle(day.getDate().toString());
        this.setScene(editScene);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(parent.getPrimary());
        this.setX(parent.getPrimary().getX() + 200);
        this.setY(parent.getPrimary().getY() + 100);
        this.show();

        AppService service = Main.getService();

        box.setPadding(new Insets(18, 8, 18, 8));

        if (day.getHostDoctors().size() != 0)
            box.getChildren().add(new Label("Отредактировать имеющиеся:"));
        day.getHostDoctors().stream()
            .map(d -> {
                try {
                    return service.getDoctorById(d.getDoctorId());
                } catch (NoSuchElementException e) {
                    return null;
                }
            })
            .forEach(doctor -> {
                if (doctor == null)
                    return;
                Button button = new Button(String.format("%s %.1s. %.1s.",
                    doctor.getLastName(), doctor.getFirstName(), doctor.getPatronymic())
                );
                button.setPrefWidth(150);
                button.setOnMouseClicked(event -> {
                    new EditStage(parent, day, doctor);
                    this.close();
                });
                box.getChildren().add(button);
            });

        box.getChildren().add(new Label("Создать нового:"));

        Button createButton = new Button("Создать запись");
        createButton.setPrefWidth(150);
        createButton.setOnMouseClicked(event -> {
            new CreateEntryStage(parent, day);
            this.close();
        });
        box.getChildren().add(createButton);
    }
}
