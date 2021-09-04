package ru.abtank.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class WorkoutExerciseId implements Serializable {
    private static final long SerialVersionUID = 3022017542654153252L;

    @Column (name = "workout_id", nullable = false)
    private Long workoutId;

    @Column (name = "exercise_id", nullable = false)
    private Integer exerciseId;

}
