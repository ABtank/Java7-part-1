package ru.abtank.persist.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abtank.persist.entities.Exercise;

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
    private String category;
    private String character;
    private String creator;
    private Date createDate;

    public ExerciseDto(Exercise exercise) {
        this.id = exercise.getId();
        this.name = exercise.getName();
        this.descr = exercise.getDescr();
        this.isCardio = exercise.getIsCardio();
        this.cardioName1 = exercise.getCardioName1();
        this.cardioName2 = exercise.getCardioName2();
        this.cardioName3 = exercise.getCardioName3();
        this.category = exercise.getCategory().getName();
        this.character = exercise.getCharacter().getName();
        this.creator = exercise.getCreator().getLogin();
        this.createDate = exercise.getCreateDate();
    }
}
