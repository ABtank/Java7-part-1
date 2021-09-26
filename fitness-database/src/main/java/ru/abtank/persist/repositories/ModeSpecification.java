package ru.abtank.persist.repositories;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.persist.entities.Mode;

public final class ModeSpecification {
//    аналог where 1=1
    public static Specification<Mode> trueLiteral(){
        return (root,quary,builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Mode> findByName(String name){
        return (root,quary,builder) -> builder.like(root.get("name"), name);
    }

    public static Specification<Mode> nameContains(String name){
        return (root,quary,builder) -> builder.like(root.get("name"), "%"+name+"%");
    }

    public static Specification<Mode> idNotEqual(Integer id){
        return (root,quary,builder) -> builder.notEqual(root.get("id"), id);
    }

}
