package com.example.storege.adapters;

import com.example.dao.EducationLevel;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

public class EducationLevelTypeAdapter extends TypeAdapter<EducationLevel> {

    @Override
    public void write(JsonWriter out, EducationLevel value) throws IOException {
        if (value != null) {
            out.value(value.name());
        } else {
            out.nullValue();
        }
    }

    @Override
    public EducationLevel read(JsonReader in) throws IOException {
        if (in.hasNext()) {
            String name = in.nextString();
            return EducationLevel.valueOf(name);
        }
        return null;
    }
}
