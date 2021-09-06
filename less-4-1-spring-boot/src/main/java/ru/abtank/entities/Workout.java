package ru.abtank.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table (name = "workouts")
@Data
@NoArgsConstructor
public class Workout implements Serializable {
    private static final long SerialVersionUID = -8475703101738989687L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private Integer id;

    @Column (name = "workout", nullable = false)
    private String name;

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
