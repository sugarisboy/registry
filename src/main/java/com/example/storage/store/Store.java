package com.example.storage.store;

import java.io.File;

public interface Store {

    void save();

    File getFile();

    String asStringContent();
}
