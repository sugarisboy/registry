package com.example.storege;

import com.example.dao.Worker;
import com.example.storege.store.AppStore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class OwnStore extends AppStore {

    @Expose
    @SerializedName("workers")
    private List<Worker> workers;

}
