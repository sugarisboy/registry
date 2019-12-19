package com.example.storage;

import com.example.dao.Worker;
import com.example.storage.store.AppStore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OwnStore extends AppStore {

    @Expose
    @SerializedName("workers")
    private List<Worker> workers = new ArrayList<>();

}
