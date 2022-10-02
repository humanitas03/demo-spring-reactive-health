package com.example.healthserviceapp.controller;

import com.example.healthserviceapp.entity.UserWorkout;
import com.example.healthserviceapp.service.UserWorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class UserWorkoutController {

    public final UserWorkoutService userWorkoutService;

    @GetMapping("")
    public UserWorkout findUserWorkoutByUserId(@RequestHeader("user-id") String userId) {
        return userWorkoutService.findWorkoutByUser(userId);
    }
}
