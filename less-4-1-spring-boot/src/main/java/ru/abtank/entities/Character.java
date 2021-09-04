package ru.abtank.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table (name = "characters")
@Data
@NoArgsConstructor
public class Character implements Serializable {
    private static final long SerialVersionUID = -3016592136035667530L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "character_id")
    private Integer characterId;

    @Column (name = "character", nullable = false, unique = true)
    @NotBlank
    private String character;

    @Column (name = "descr")
    private String descr;
}
