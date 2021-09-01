package ru.abtank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.abtank.model.Box;
import ru.abtank.repositories.BoxRepository;

import java.util.List;

@Component
public class BoxService {
    private BoxRepository boxRepository;

    @Autowired
    public void setBoxRepository(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public List<Box> getAllBoxes() {
        return boxRepository.getAllBoxes();
    }

    public void save(Box box) {
        boxRepository.save(box);
    }

    public void deleteById(Long id) {
        boxRepository.deleteById(id);
    }
}
