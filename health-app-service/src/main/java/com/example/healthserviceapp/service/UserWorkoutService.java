package com.example.healthserviceapp.service;

import com.example.healthserviceapp.entity.UserWorkout;
import com.example.healthserviceapp.repository.UserWorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserWorkoutService {

    private final UserWorkoutRepository userworkoutRepository;

    @Transactional(readOnly = true)
    public UserWorkout findWorkoutByUser(String user) {
        return userworkoutRepository.findByUser(user);
    }

}
