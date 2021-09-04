package ru.abtank.dto;

import java.util.Date;

public interface ExerciseDto {

    Integer getExerciseId();

    String getExercise();

    String getDescr();

    Boolean getIsCardio();

    String getCardioName1();

    String getCardioName2();

    String getCardioName3();

    Date getCreateDate();


}
