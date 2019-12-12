package com.example.service;

import com.example.dao.EducationLevel;
import com.example.dao.Worker;
import com.example.storage.OwnStore;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class AppService {

    @Getter
    private final OwnStore ownStore;

    public AppService(OwnStore ownStore) {
        this.ownStore = ownStore;
    }

    public void addWorker(Worker w) {
        ownStore.getWorkers().add(w);
        ownStore.save();
    }

    public void removeWorker(Worker w) {
        ownStore.getWorkers().remove(w);
        ownStore.save();
    }

    /*public Worker findById(Long id) {

    }*/

    public List<Worker> findByFilter(EducationLevel education, String department) {
        return ownStore.getWorkers().stream()
            .filter(w -> w.getEducationLevel().equals(education))
            .filter(w -> w.getDepartment().equalsIgnoreCase(department))
            .collect(Collectors.toList());
    }

    public List<Worker> findByAge(int minAge, int maxAge) {
        return ownStore.getWorkers().stream()
            .filter(w -> w.getAge() <= maxAge && w.getAge() >= minAge)
            .collect(Collectors.toList());
    }
}
