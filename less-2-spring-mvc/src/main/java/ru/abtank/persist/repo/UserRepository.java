package ru.abtank.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User ,Integer> {

    List<User> findByLogin(String login);
    List<User> findByLoginLike(String loginPattern);
    List<User> findByEmailLike(String emailPattern);
    List<User> findByEmailLikeAndLoginLike(String emailPattern, String loginPattern);
}
