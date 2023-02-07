package com.example.javaex.user.workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutModelDetailsService {

    private final WorkoutModelRepository workoutModelRepository;

    @Autowired
    public WorkoutModelDetailsService(WorkoutModelRepository workoutModelRepository){
        this.workoutModelRepository = workoutModelRepository;
    }

    public void save(WorkoutModel workoutModel){
        workoutModelRepository.save(workoutModel);
    }

}
