package com.example.storage.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value != null) {
            out.value(
                String.format("%d.%d.%d",
                    value.getDayOfMonth(),
                    value.getMonth().getValue(),
                    value.getYear()
                )
            );
        } else {
            out.nullValue();
        }
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        String value = in.nextString();
        String[] values = value.split("\\.");
        if (values.length == 3) {
            try {
                int day = Integer.valueOf(values[0]);
                int month = Integer.valueOf(values[1]);
                int year = Integer.valueOf(values[2]);
                return LocalDate.of(year, month, day);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка чтения даты рождения.");
            }
        }
        return LocalDate.of(1970, 1, 1);
    }
}
