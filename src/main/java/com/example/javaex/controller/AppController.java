package com.example.javaex.controller;

import com.example.javaex.config.AppPasswordConfig;
import com.example.javaex.config.HibernateAnnotationUtil;
import com.example.javaex.user.UserModel;
import com.example.javaex.user.UserModelDetailsService;
import com.example.javaex.user.workout.WorkoutModel;
import com.example.javaex.user.workout.WorkoutModelDetailsService;
import com.example.javaex.weatherAPI.WeatherWebClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController /*RestController for postman*/
@RequestMapping("/api")
public class  AppController {


    private final UserModelDetailsService userModelDetailsService;
    private final WorkoutModelDetailsService workoutModelDetailsService;
    private final WeatherWebClient weatherWebClient;
    private final AppPasswordConfig appPasswordConfig;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AppController(UserModelDetailsService userModelDetailsService, WorkoutModelDetailsService workoutModelDetailsService, WeatherWebClient weatherWebClient, AppPasswordConfig appPasswordConfig, AuthenticationManager authenticationManager) {
        this.userModelDetailsService = userModelDetailsService;
        this.workoutModelDetailsService = workoutModelDetailsService;
        this.weatherWebClient = weatherWebClient;
        this.appPasswordConfig = appPasswordConfig;
        this.authenticationManager = authenticationManager;
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
        userModelDetailsService.save(userModel);}

    @PostMapping("/{userid}/saveNewWorkout")
    public void saveNewWorkout(@RequestBody WorkoutModel workoutModel, @PathVariable("userid")Long userid){
        UserModel userModelGettingWorkOut = findUserById(userid);
        System.out.println(userModelGettingWorkOut);
        System.out.println(workoutModel);
        Set<WorkoutModel> workoutModelSet = new HashSet<>();
        workoutModelSet.add(workoutModel);
        System.out.println(workoutModelSet);
        System.out.println(userModelGettingWorkOut);

        SessionFactory sessionFactory = HibernateAnnotationUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();
        workoutModel = session.get(WorkoutModel.class, userid);
        tx.commit();
        System.out.println(workoutModel);
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

    @GetMapping("/signin")
    public void signin(@RequestParam("username") String username, @RequestParam("password") String password){

        try {
            // Create an authentication token with the provided username and password
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

            // Perform authentication
            Authentication authenticated = authenticationManager.authenticate(authentication);

            // Set the authenticated user in the security context
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            // Authentication successful, proceed with further actions
            System.out.println("User authenticated successfully.");

            // Redirect the user to a success page or perform any other necessary actions

        } catch (Exception e) {
            // Authentication failed, handle the error accordingly
            System.out.println("Invalid username or password.");

            // Redirect the user to an error page or perform any other necessary actions
        }}

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

        @PostMapping("/userModels")
        public ResponseEntity<UserModel> createUserModel(@RequestBody UserModel userModel) {
            UserModel _userModel = userModelDetailsService.save(userModel);
            return new ResponseEntity<>(_userModel, HttpStatus.CREATED);
        }


        @PutMapping("/userModels/{id}")
        public ResponseEntity<UserModel> updateUserModel(@PathVariable("id") long id, @RequestBody UserModel userModel) {
            UserModel _userModel = userModelDetailsService.findById(id);

            _userModel.setName(userModel.getName());
            _userModel.setUsername(userModel.getUsername());
            _userModel.setPassword(userModel.getPassword());

            return new ResponseEntity<>(userModelDetailsService.save(_userModel), HttpStatus.OK);
        }

        @DeleteMapping("/userModels/{id}")
        public ResponseEntity<HttpStatus> deleteUserModel(@PathVariable("id") long id) {
            userModelDetailsService.deleteById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @DeleteMapping("/userModels")
        public ResponseEntity<HttpStatus> deleteAllUserModels() {
            userModelDetailsService.deleteAll();

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @GetMapping("/userModels/registeredWorkouts")
        public ResponseEntity<List<UserModel>> findByHasRegisteredWorkouts() {
            List<UserModel> userModels = userModelDetailsService.findByHasRegisteredWorkouts(true);

            if (userModels.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(userModels, HttpStatus.OK);
        }


    @GetMapping("/userModels/{userModelId}/workoutModels")
    public ResponseEntity<List<WorkoutModel>> getAllWorkoutModelsByUserModelId(@PathVariable(value = "userModelId") Long userModelId) {

        List<WorkoutModel> workoutModels = workoutModelDetailsService.findByUserModelId(userModelId);
        return new ResponseEntity<>(workoutModels, HttpStatus.OK);
    }

    @GetMapping("/workoutModels/{id}")
    public ResponseEntity<WorkoutModel> getWorkoutModelsByUserModelId(@PathVariable(value = "id") Long id) {
        WorkoutModel workoutModel = workoutModelDetailsService.findById(id);

        return new ResponseEntity<>(workoutModel, HttpStatus.OK);
    }

    @PostMapping("/userModels/{userModelId}/workoutModels")
    public ResponseEntity<WorkoutModel> createWorkoutModel(@PathVariable(value = "userModelId") Long userModelId,
                                                 @RequestBody WorkoutModel workoutModelRequest) {

        UserModel userModelToSave = userModelDetailsService.findById(userModelId);
        WorkoutModel workoutModelToSave = workoutModelDetailsService.save(workoutModelRequest);
        workoutModelToSave.setUserModel(userModelToSave);

        return new ResponseEntity<>(workoutModelToSave, HttpStatus.CREATED);
    }

    @PutMapping("/workoutModels/{id}")
    public ResponseEntity<WorkoutModel> updateWorkoutModel(@PathVariable("id") long id, @RequestBody WorkoutModel workoutModelRequest) {
        WorkoutModel workoutModel = workoutModelDetailsService.findById(id);

        workoutModel.setDateOfWorkout(workoutModelRequest.getDateOfWorkout());
        workoutModel.setTimeStartWorkout(workoutModelRequest.getTimeStartWorkout());
        workoutModel.setTimeEndWorkout(workoutModelRequest.getTimeEndWorkout());
        workoutModel.setWorkoutCategory(workoutModelRequest.getWorkoutCategory());
        workoutModel.setDistance(workoutModelRequest.getDistance());

        return new ResponseEntity<>(workoutModelDetailsService.save(workoutModel), HttpStatus.OK);
    }

    @DeleteMapping("/workoutModels/{id}")
    public ResponseEntity<HttpStatus> deleteWorkoutModel(@PathVariable("id") long id) {
        workoutModelDetailsService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/userModels/{userModelId}/workoutModels")
    public ResponseEntity<List<WorkoutModel>> deleteAllWorkoutModelsOfUserModel(@PathVariable(value = "userModelId") Long userModelId) {

        workoutModelDetailsService.deleteByUserModelId(userModelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
