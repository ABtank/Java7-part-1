package ru.abtank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.abtank.entities.*;
import ru.abtank.entities.Character;

import java.io.ObjectStreamClass;

@SpringBootApplication
public class Less41SpringBootApplication {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	public static void main(String[] args) {
		SpringApplication.run(Less41SpringBootApplication.class, args);
//		System.out.println("/////////////SerialVersionUID///////////////");
//		System.out.println("category "+ObjectStreamClass.lookup(Category.class).getSerialVersionUID());
//		System.out.println("WorkoutExerciseId "+ObjectStreamClass.lookup(WorkoutExerciseId.class).getSerialVersionUID());
//		System.out.println("WorkoutExercise "+ObjectStreamClass.lookup(WorkoutExercise.class).getSerialVersionUID());
//		System.out.println("Character "+ObjectStreamClass.lookup(Character.class).getSerialVersionUID());
//		System.out.println("Exercise "+ObjectStreamClass.lookup(Exercise.class).getSerialVersionUID());
//		System.out.println("Mode "+ObjectStreamClass.lookup(Mode.class).getSerialVersionUID());
//		System.out.println("Set "+ObjectStreamClass.lookup(Set.class).getSerialVersionUID());
//		System.out.println("Workout "+ObjectStreamClass.lookup(Workout.class).getSerialVersionUID());
	}

}
