package com.example;

import com.example.scenes.MainScene;
import com.example.service.AppService;
import com.example.storage.OwnStore;
import com.example.storage.StoreLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

public class Main extends Application {

    @Getter
    private static AppService service;

    @Override
    public void start(final Stage stage) {
        StoreLoader storeLoader = new StoreLoader();
        OwnStore store = storeLoader.loadOwnStore();
        service = new AppService(store);

        Scene scene = new MainScene(stage);

        stage.setTitle("Регистратура");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
