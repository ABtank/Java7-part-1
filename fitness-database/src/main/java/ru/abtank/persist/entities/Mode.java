package ru.abtank.persist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "modes")
@Data
@NoArgsConstructor
public class Mode implements Serializable {
    private static final long SerialVersionUID = 6916824256395479982L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mode_id")
    private Integer id;

    @Column(name = "mode", nullable = false, unique = true)
    private String name;

    @Column(name = "is_start")
    private Boolean isStart;

    @Column(name = "descr")
    private String descr;
}
