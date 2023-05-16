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
    public List<WorkoutModel> findAll(){
        return workoutModelRepository.findAll();
    }


    public WorkoutModel save(WorkoutModel workoutModel){
        return workoutModelRepository.save(workoutModel);
    }

    public void deleteById(Long id){
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

    public List<WorkoutModel> findByUserModelId(Long userModelId) {
        return workoutModelRepository.findByUserModelId(userModelId);
    }

    public WorkoutModel findById(Long id) {
        Optional<WorkoutModel> result = workoutModelRepository.findById(id);

        WorkoutModel foundWorkout;

        if(result.isPresent()){
            foundWorkout=result.get();
        }else{
            throw new RuntimeException("Did not find id: " + id);
        }
        return foundWorkout;
    }

    public void deleteByUserModelId(Long userModelId) {
        workoutModelRepository.deleteByUserModelId(userModelId);
    }
}
