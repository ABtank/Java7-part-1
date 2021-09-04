package ru.abtank.repositories;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.entities.User;

public final class UserSpecification {
//    аналог where 1=1
    public static Specification<User> trueLiteral(){
        return (root,quary,builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<User> loginLike(String login){
        return (root,quary,builder) -> builder.like(root.get("login"), login);
    }

    public static Specification<User> loginContains(String login){
        return (root,quary,builder) -> builder.like(root.get("login"), "%"+login+"%");
    }

    public static Specification<User> emailContains(String email){
        return (root,quary,builder) -> builder.like(root.get("email"), "%"+email+"%");
    }

}
