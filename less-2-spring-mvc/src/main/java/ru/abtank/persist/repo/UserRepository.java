package ru.abtank.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User ,Integer>, JpaSpecificationExecutor<User> {

    List<User> findByLogin(String login);
    List<User> findByLoginLike(String loginPattern);
    List<User> findByEmailLike(String emailPattern);
    List<User> findByEmailLikeAndLoginLike(String emailPattern, String loginPattern);

    @Query("FROM User u " +
            "WHERE (u.email = :emailPattern OR u.email IS NULL) " +
            "AND (u.login = :loginPattern OR u.login IS NULL)")
    List<User> queryByEmailLikeAndLoginLike (@Param ("email") String emailPattern,@Param ("login") String loginPattern);
}
