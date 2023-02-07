package com.example.javaex.user.workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void delete(Long id){
        workoutModelRepository.deleteById(id);
    }

    public List<WorkoutModel> findAllWorkouts(){
        return workoutModelRepository.findAll();
    }

    public WorkoutModel findbyId(Long id){
        Optional<WorkoutModel> result = workoutModelRepository.findById(id);

        WorkoutModel foundWorkout;

        if(result.isPresent()){foundWorkout=result.get();
        }else{
            throw new RuntimeException("Did not find id: " + id);
        }
        return foundWorkout;
    }

    public WorkoutModel findByWorkoutCategory(String name){
        return workoutModelRepository.findByWorkoutCategory(name);
    }

}
