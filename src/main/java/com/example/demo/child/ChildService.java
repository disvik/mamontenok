package com.example.demo.child;

import com.example.demo.child.Child;
import com.example.demo.child.ChildRepository;
import com.example.demo.user.Favourite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService {

    private ChildRepository repository;

    @Autowired
    public void setRepository(ChildRepository repository) {
        this.repository = repository;
    }

    public void saveChild(Child child) {
        repository.save(child);
    }

    public void addChild(Child child) {
        repository.save(child);
    }

    public Child getChildById(long id)
    {
        return repository.getOne(id);
    }


    public List<Child> getAllChildren() {
        return repository.findAll();
    }

    public void deleteChild (long id) {
        repository.deleteById(id);
    }
}