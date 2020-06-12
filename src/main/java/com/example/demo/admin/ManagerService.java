package com.example.demo.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    private ManagerRepository repository;

    @Autowired
    public void setRepository(ManagerRepository repository) {
        this.repository = repository;
    }

    public void saveManager(Manager manager) {
        repository.save(manager);
    }

    public void addManager(Manager manager) {
        repository.save(manager);
    }

    public List<Manager> getAllManagers() {
        return repository.findAll();
    }

    public Manager getManagerById(long id)
    {
        return repository.getOne(id);
    }

}