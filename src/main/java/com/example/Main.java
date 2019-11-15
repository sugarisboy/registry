package com.example;

import com.example.storege.OwnStore;
import com.example.storege.StoreLoader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(final Stage stage) {
        StoreLoader storeLoader = new StoreLoader();
        OwnStore ownStore = storeLoader.loadOwnStore();

        Group root = new Group();
        Scene scene = new Scene(root, 1280, 720);

        stage.setTitle("Регистратура");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
