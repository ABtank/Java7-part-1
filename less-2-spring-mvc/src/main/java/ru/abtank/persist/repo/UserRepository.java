package ru.abtank.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abtank.persist.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User ,Integer> {

}
