package ru.abtank.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
public class Exercise implements Serializable {
    private static final long SerialVersionUID = -3928198721940288434L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Integer id;

    @Column(name = "exercise", unique = true, nullable = false)
    private String name;

    @Column(name = "descr")
    private String descr;

    @Column(name = "is_cardio",nullable = false)
    private Boolean isCardio;

    @Column(name = "cardio_name_1")
    private String cardioName1;

    @Column(name = "cardio_name_2")
    private String cardioName2;

    @Column(name = "cardio_name_3")
    private String cardioName3;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_create", updatable = false, nullable = false)
    private Date createDate;


    public Date getCreateDate() {
        return createDate;
    }

    @PrePersist
    public void setCreateDate() {
        this.createDate = new Date();
    }

}
