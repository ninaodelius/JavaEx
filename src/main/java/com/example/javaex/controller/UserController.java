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

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController /*RestController for postman*/
@RequestMapping("/api")
public class UserController {

    private final UserModelDetailsService userModelDetailsService;
    private final WorkoutModelDetailsService workoutModelDetailsService;
    private final WeatherWebClient weatherWebClient;
    private final AppPasswordConfig appPasswordConfig;
    //private final AuthenticationManager authenticationManager;
    private final UserModelRepository userModelRepository;
    @Autowired
    public UserController(UserModelDetailsService userModelDetailsService, WorkoutModelDetailsService workoutModelDetailsService, WeatherWebClient weatherWebClient, AppPasswordConfig appPasswordConfig,/* AuthenticationManager authenticationManager,*/ UserModelRepository userModelRepository) {
        this.userModelDetailsService = userModelDetailsService;
        this.workoutModelDetailsService = workoutModelDetailsService;
        this.weatherWebClient = weatherWebClient;
        this.appPasswordConfig = appPasswordConfig;
       // this.authenticationManager = authenticationManager;
        this.userModelRepository = userModelRepository;
    }

    @GetMapping("/home")
    public List<UserModel> listAllUsers(){
        return userModelDetailsService.findAll();
    }

    @GetMapping("/{id}")
    public UserModel findUserById(@PathVariable("id") Long id){
        return userModelDetailsService.findById(id);
    }


    @PostMapping("/save")
    public void saveNewUser(@RequestBody UserModel userModel){
        userModelDetailsService.save(userModel);

    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id")Long id){ userModelDetailsService.deleteById(id);}

    @PutMapping("/{id}")
    public void updateUserById(@PathVariable("id")Long id, @RequestBody UserModel userModel){
        userModelDetailsService.updateUser(id,userModel);
    }

    @GetMapping("/test")
    public void test(){
        System.out.println("hello");
    }


    /** User controller **/

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers(@RequestParam(required = false) String name) {
        List<UserModel> users = new ArrayList<UserModel>();

        if (name == null)
            userModelDetailsService.findAll().forEach(users::add);
        else
            userModelDetailsService.findByNameContaining(name).forEach(users::add);

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUserModelById(@PathVariable("id") long id) {
        UserModel userModel = userModelDetailsService.findById(id);

        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserModel> createUserModel(@RequestBody UserModel userModel) {
        UserModel _userModel = userModelDetailsService.save(userModel);
        return new ResponseEntity<>(_userModel, HttpStatus.CREATED);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<UserModel> updateUserModel(@PathVariable("id") long id, @RequestBody UserModel userModel) {
        UserModel _userModel = userModelDetailsService.findById(id);

        _userModel.setName(userModel.getName());
        _userModel.setUsername(userModel.getUsername());
        _userModel.setPassword(userModel.getPassword());
        _userModel.setHasRegisteredWorkouts(userModel.isHasRegisteredWorkouts());

        return new ResponseEntity<>(userModelDetailsService.save(_userModel), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUserModel(@PathVariable("id") long id) {
        userModelDetailsService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteAllUserModels() {
        userModelDetailsService.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/hasWorkouts")
    public ResponseEntity<List<UserModel>> findByHasRegisteredWorkouts() {
        List<UserModel> userModels = userModelDetailsService.findByHasRegisteredWorkouts(true);

        if (userModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userModels, HttpStatus.OK);
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

        //UserModel userModelToSave = userModelDetailsService.findById(userModelId);
        //WorkoutModel workoutModelToSave = workoutModelDetailsService.save(workoutModelRequest);
        //workoutModelToSave.setUserModel(userModelToSave);
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
