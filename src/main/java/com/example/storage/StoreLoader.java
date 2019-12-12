package com.example.storage;

import com.example.dao.EducationLevel;
import com.example.storage.adapters.EducationLevelTypeAdapter;
import com.example.storage.adapters.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Scanner;

public class StoreLoader {

    private final String path = "C:\\deletethis\\store.json";

    public OwnStore loadOwnStore() {
        File dir = new File(Paths.get(path).getParent().toUri());
        if (!dir.exists())
            dir.mkdir();

        File store = new File(path);
        if (!store.exists()) {
            Path scheme = Paths.get("store.json");
            try {
                Files.copy(scheme, store.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Неудалось создать стандартный файл хранилища.");
                e.printStackTrace();
            }
        }

        String json = "";
        try {
            Scanner scan = new Scanner(store);
            while (scan.hasNextLine()) {
                json += scan.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка, файл не найден!");
        }

        OwnStore ownStore = null;
        try {
            ownStore = gson().fromJson(json, OwnStore.class);
            ownStore.setFile(store);
        } catch (JsonSyntaxException e) {
            System.out.println("Store file parse exception:\n" + e.getMessage());
        }

        return ownStore;
    }

    public static Gson gson() {
        return new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
            .registerTypeAdapter(EducationLevel.class, new EducationLevelTypeAdapter())
            .setPrettyPrinting()
            .create();
    }
}
