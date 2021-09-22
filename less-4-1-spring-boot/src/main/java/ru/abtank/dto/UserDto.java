package ru.abtank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.abtank.persist.entities.Role;
import ru.abtank.persist.entities.User;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String login;
    private String email;
    private Set<Role> roles;
    private User creator;
    private Date createDate;
    private Date modifyDate;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.roles = user.getRoles();
        this.creator = user.getCreator();
        this.createDate = user.getCreateDate();
        this.modifyDate = user.getModifyDate();
    }
}
