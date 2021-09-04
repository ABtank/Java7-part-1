package ru.abtank.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "workouts_exercises")
@Data
@NoArgsConstructor
public class WorkoutExercise implements Serializable {
    private static final long SerialVersionUID = 1153699633365897601L;

    @EmbeddedId
    private WorkoutExerciseId workoutExerciseId;

    @ManyToOne
    @JoinColumn(name = "mode_id", nullable = false)
    private Mode modeId;

    @Column (name = "ordinal", nullable = false)
    private Integer ordinal;

    @Column (name = "descr")
    private String descr;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_create", updatable=false)
    private Date createDate;


    public Date getCreateDate() {
        return createDate;
    }
    @PrePersist
    public void setCreateDate() {
        this.createDate = new Date();
    }

}
