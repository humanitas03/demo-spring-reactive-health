package com.example.healthserviceapp.controller;

import com.example.healthserviceapp.entity.UserWorkout;
import com.example.healthserviceapp.service.UserWorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
@Slf4j
public class UserWorkoutController {

    public final UserWorkoutService userWorkoutService;

    @GetMapping("")
    public UserWorkout findUserWorkoutByUserId(@RequestHeader("x-user-id") String userId) {
        log.info("====> userid :  {}", userId);
        return userWorkoutService.findWorkoutByUser(userId);
    }
}
