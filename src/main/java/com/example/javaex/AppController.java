package com.example.javaex;

import com.example.javaex.config.AppPasswordConfig;
import com.example.javaex.user.UserModel;
import com.example.javaex.user.UserModelDetailsService;
import com.example.javaex.user.workout.WorkoutModelDetailsService;
import com.example.javaex.weatherAPI.WeatherWebClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController /*RestController for postman*/
@RequestMapping("/api")
public class AppController {

    private final UserModelDetailsService userModelDetailsService;
    private final WorkoutModelDetailsService workoutModelDetailsService;
    private final WeatherWebClient weatherWebClient;
    private final AppPasswordConfig appPasswordConfig;

    public AppController(UserModelDetailsService userModelDetailsService, WorkoutModelDetailsService workoutModelDetailsService, WeatherWebClient weatherWebClient, AppPasswordConfig appPasswordConfig) {
        this.userModelDetailsService = userModelDetailsService;
        this.workoutModelDetailsService = workoutModelDetailsService;
        this.weatherWebClient = weatherWebClient;
        this.appPasswordConfig = appPasswordConfig;
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

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id")Long id){ userModelDetailsService.delete(id);}

    @PutMapping("/{id}")
    public void updateUserById(@PathVariable("id")Long id, @RequestBody UserModel userModel){
        userModelDetailsService.updateUser(id,userModel);
    }

    @GetMapping("/test")
    public void test(){
        System.out.println("hello");
    }

    //@GetMapping("/signin")
    //public void signin(@RequestParam("username") String username, @RequestParam("password") String password){

    //}
}
