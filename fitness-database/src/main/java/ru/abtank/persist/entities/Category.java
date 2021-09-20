package ru.abtank.persist.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category implements Serializable {
    private static final long SerialVersionUID = 1719796177095347955L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @Column (name = "category", nullable = false, unique = true)
    @NotBlank (message = "Укажите категорию")
    private String name;

    @Column (name = "descr")
    private String descr;

    @ManyToOne
    @JoinColumn (name = "creator_id", nullable = false)
    private User creator;

}
