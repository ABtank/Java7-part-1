package ru.abtank.repositories;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.entities.Exercise;

public final class ExerciseSpecification {
//    аналог where 1=1
    public static Specification<Exercise> trueLiteral(){
        return (root,quary,builder) -> builder.isTrue(builder.literal(true));
    }

    public static Specification<Exercise> findByExercise(String exercise){
        return (root,quary,builder) -> builder.like(root.get("exercise"), exercise);
    }

    public static Specification<Exercise> exerciseContains(String exercise){
        return (root,quary,builder) -> builder.like(root.get("exercise"), "%"+exercise+"%");
    }

}
