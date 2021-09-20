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
    private Set<String> roles;
    private String creator;
    private Date createDate;
    private Date modifyDate;

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream().map(Role :: getName).collect(Collectors.toSet());
        this.creator = user.getCreator().getLogin();
        this.createDate = user.getCreateDate();
        this.modifyDate = user.getModifyDate();
    }
}
