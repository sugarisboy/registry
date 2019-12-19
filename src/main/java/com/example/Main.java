package com.example;

import com.example.scenes.MainScene;
import com.example.service.AppService;
import com.example.storage.OwnStore;
import com.example.storage.StoreLoader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.applet.AppletStub;

public class Main extends Application {

    @Getter
    private static AppService service;

    @Getter
    private static Main app;

    @Getter
    private StoreLoader storeLoader;

    @Getter
    private OwnStore store;

    private MainScene mainScene;

    @Override
    public void start(final Stage stage) {
        app = this;
        this.storeLoader = new StoreLoader();
        this.store = new OwnStore();

        service = new AppService(store);

        mainScene = new MainScene(stage);

        stage.setTitle("Регистратура");
        stage.setScene(mainScene);
        stage.show();
    }

    public void setStore(OwnStore store) {
        this.store = store;
        mainScene.getWorkerList().setItems(FXCollections.observableList(store.getWorkers()));
    }

    public static void main(String[] args) { launch(args); }
}
