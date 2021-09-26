package ru.abtank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abtank.persist.entities.Role;

@Data
@NoArgsConstructor
public class RoleDto {
    private Integer id;
    private String name;

    public RoleDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
