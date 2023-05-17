package com.example.javaex.controller;

import com.example.javaex.config.AppPasswordConfig;
import com.example.javaex.user.UserModel;
import com.example.javaex.user.UserModelDetailsService;
import com.example.javaex.user.UserModelRepository;
import com.example.javaex.user.workout.WorkoutModel;
import com.example.javaex.user.workout.WorkoutModelDetailsService;
import com.example.javaex.weatherAPI.WeatherWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController /*RestController for postman*/
@RequestMapping("/api")
public class WorkoutController {


    private final UserModelDetailsService userModelDetailsService;
    private final WorkoutModelDetailsService workoutModelDetailsService;
    private final WeatherWebClient weatherWebClient;
    private final AppPasswordConfig appPasswordConfig;
    //private final AuthenticationManager authenticationManager;
    private final UserModelRepository userModelRepository;

    @Autowired
    public WorkoutController(UserModelDetailsService userModelDetailsService, WorkoutModelDetailsService workoutModelDetailsService, WeatherWebClient weatherWebClient, AppPasswordConfig appPasswordConfig,/* AuthenticationManager authenticationManager,*/ UserModelRepository userModelRepository) {
        this.userModelDetailsService = userModelDetailsService;
        this.workoutModelDetailsService = workoutModelDetailsService;
        this.weatherWebClient = weatherWebClient;
        this.appPasswordConfig = appPasswordConfig;
        // this.authenticationManager = authenticationManager;
        this.userModelRepository = userModelRepository;
    }
    /** Workout controller **/

    @GetMapping("/users/{userModelId}/workouts")
    public ResponseEntity<List<WorkoutModel>> getAllWorkoutModelsByUserModelId(@PathVariable(value = "userModelId") Long userModelId) {

        List<WorkoutModel> workoutModels = workoutModelDetailsService.findByUserModelId(userModelId);
        return new ResponseEntity<>(workoutModels, HttpStatus.OK);
    }

    @GetMapping("/workouts/{id}")
    public ResponseEntity<WorkoutModel> getWorkoutModelsByUserModelId(@PathVariable(value = "id") Long id) {
        WorkoutModel workoutModel = workoutModelDetailsService.findById(id);

        return new ResponseEntity<>(workoutModel, HttpStatus.OK);
    }
    @GetMapping("/workouts/{userModelId}/total")
    public long getTotalWorkoutsByUserModelId(@PathVariable(value = "userModelId") Long userModelId) {
        return workoutModelDetailsService.count(userModelId);
    }
    @PostMapping("/users/{userModelId}/workouts")
    public ResponseEntity<WorkoutModel> createWorkoutModel(@PathVariable(value = "userModelId") Long userModelId,
                                                           @RequestBody WorkoutModel workoutModelRequest) throws Exception {

        WorkoutModel workoutModelToSave = userModelRepository.findById(userModelId).map(userModel -> {
                    workoutModelRequest.setUserModel(userModel);
                    UserModel userModelGettingWorkout = userModelDetailsService.findById(userModelId);
                    userModelGettingWorkout.setHasRegisteredWorkouts(true);
                    userModelDetailsService.save(userModelGettingWorkout);
                    return workoutModelDetailsService.save(workoutModelRequest);
                })
                .orElseThrow(() -> new Exception("Not found user with id = " + userModelId));

        return new ResponseEntity<>(workoutModelToSave, HttpStatus.CREATED);
    }

    @PutMapping("/workouts/{id}")
    public ResponseEntity<WorkoutModel> updateWorkoutModel(@PathVariable("id") long id, @RequestBody WorkoutModel workoutModelRequest) {
        WorkoutModel workoutModel = workoutModelDetailsService.findById(id);

        workoutModel.setDateOfWorkout(workoutModelRequest.getDateOfWorkout());
        workoutModel.setTimeStartWorkout(workoutModelRequest.getTimeStartWorkout());
        workoutModel.setTimeEndWorkout(workoutModelRequest.getTimeEndWorkout());
        workoutModel.setWorkoutCategory(workoutModelRequest.getWorkoutCategory());
        workoutModel.setDistance(workoutModelRequest.getDistance());

        return new ResponseEntity<>(workoutModelDetailsService.save(workoutModel), HttpStatus.OK);
    }

    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<HttpStatus> deleteWorkoutModel(@PathVariable("id") long id) {
        workoutModelDetailsService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/{userModelId}/workouts")
    public ResponseEntity<List<WorkoutModel>> deleteAllWorkoutModelsOfUserModel(@PathVariable(value = "userModelId") Long userModelId) {

        workoutModelDetailsService.deleteByUserModelId(userModelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
