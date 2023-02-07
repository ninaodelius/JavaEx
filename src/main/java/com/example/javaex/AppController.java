package com.example.javaex;

import com.example.javaex.user.UserModelDetailsService;
import com.example.javaex.user.workout.WorkoutModelDetailsService;
import org.springframework.stereotype.Controller;

@Controller
public class AppController {

    private final UserModelDetailsService userModelDetailsService;
    private final WorkoutModelDetailsService workoutModelDetailsService;

    public AppController(UserModelDetailsService userModelDetailsService, WorkoutModelDetailsService workoutModelDetailsService) {
        this.userModelDetailsService = userModelDetailsService;
        this.workoutModelDetailsService = workoutModelDetailsService;
    }



}
