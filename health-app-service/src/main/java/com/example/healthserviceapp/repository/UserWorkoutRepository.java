package com.example.healthserviceapp.repository;

import com.example.healthserviceapp.entity.UserWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWorkoutRepository extends JpaRepository<UserWorkout, Long> {
    UserWorkout findByUser(String user);
}
