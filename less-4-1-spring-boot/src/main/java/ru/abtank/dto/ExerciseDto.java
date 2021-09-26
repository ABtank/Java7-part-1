package ru.abtank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abtank.persist.entities.Category;
import ru.abtank.persist.entities.Character;
import ru.abtank.persist.entities.Exercise;
import ru.abtank.persist.entities.User;

import java.util.Date;

@Data
@NoArgsConstructor
public class ExerciseDto {

    private Integer id;
    private String name;
    private String descr;
    private Boolean isCardio;
    private String cardioName1;
    private String cardioName2;
    private String cardioName3;
    private Category category;
    private Character character;
    private User creator;
    private Date createDate;

    public ExerciseDto(Exercise exercise) {
        this.id = exercise.getId();
        this.name = exercise.getName();
        this.descr = exercise.getDescr();
        this.isCardio = exercise.getIsCardio();
        this.cardioName1 = exercise.getCardioName1();
        this.cardioName2 = exercise.getCardioName2();
        this.cardioName3 = exercise.getCardioName3();
        this.category = exercise.getCategory();
        this.character = exercise.getCharacter();
        this.creator = exercise.getCreator();
        this.createDate = exercise.getCreateDate();
    }
}
