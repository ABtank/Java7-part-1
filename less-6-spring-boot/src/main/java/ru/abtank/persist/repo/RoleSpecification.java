package ru.abtank.persist.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.persist.entity.Role;

public final class RoleSpecification {
//    аналог where 1=1
    public static Specification<Role> trueLiteral(){
        return (root,quary,builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Role> nameLike(String name){
        return (root,quary,builder) -> builder.like(root.get("name"), name);
    }

    public static Specification<Role> nameContains(String name){
        return (root,quary,builder) -> builder.like(root.get("name"), "%"+name+"%");
    }

    public static Specification<Role> emailContains(String email){
        return (root,quary,builder) -> builder.like(root.get("email"), "%"+email+"%");
    }

}
