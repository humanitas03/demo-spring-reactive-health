package com.example.healthserviceapp.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserWorkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String user;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private int dificulty;

    public static UserWorkout of(
        String user,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int dificulty
    ) {
        return new UserWorkout(
            null, user, startTime, endTime, dificulty
        );
    }
}
