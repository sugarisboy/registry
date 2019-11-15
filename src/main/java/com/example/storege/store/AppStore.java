package com.example.storege.store;

import com.example.storege.StoreLoader;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AppStore implements Store {

    @Setter
    @Getter
    private transient File file;

    @Override
    public void save() {
        if (file.canWrite()) {
            try {
                try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
                    writer.write(asStringContent());
                }
            } catch (IOException e) {
                System.out.println("Ошибка записи изменений.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public String asStringContent() {
        return StoreLoader.gson().toJson(this);
    }
}
