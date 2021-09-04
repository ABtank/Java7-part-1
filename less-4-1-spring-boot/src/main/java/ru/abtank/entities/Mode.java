package ru.abtank.entities;

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
    private Integer modeId;

    @Column(name = "mode", nullable = false, unique = true)
    private String mode;

    @Column(name = "is_start")
    private Boolean is_start;

    @Column(name = "descr")
    private String descr;
}
