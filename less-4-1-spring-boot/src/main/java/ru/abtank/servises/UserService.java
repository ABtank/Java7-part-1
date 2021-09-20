package ru.abtank.servises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import ru.abtank.dto.UserDto;
import ru.abtank.persist.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<User> findById (Integer id);
    Optional<User> findByLogin (String login);
    List<UserDto> findAll();
    List<User> findAll(Specification spec);
    Page<UserDto> findAll(Map<String, String> params, PageRequest pageRequest);
    User save(User user);
    void deleteById(Integer id);

}
