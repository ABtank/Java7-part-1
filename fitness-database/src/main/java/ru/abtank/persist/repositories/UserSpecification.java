package ru.abtank.persist.repositories;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.persist.entities.User;

public final class UserSpecification {
//    аналог where 1=1
    public static Specification<User> trueLiteral(){
        return (root,quary,builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<User> findBylogin(String login){
        return (root,quary,builder) -> builder.like(root.get("login"), login);
    }

    public static Specification<User> findByEmail(String email){
        return (root,quary,builder) -> builder.like(root.get("email"), email);
    }

    public static Specification<User> loginContains(String login){
        return (root,quary,builder) -> builder.like(root.get("login"), "%"+login+"%");
    }

    public static Specification<User> emailContains(String email){
        return (root,quary,builder) -> builder.like(root.get("email"), "%"+email+"%");
    }

    public static Specification<User> idNotEqual(Integer id){
        return (root,quary,builder) -> builder.notEqual(root.get("id"), id);
    }

}
