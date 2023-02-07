package com.example.javaex;

import com.example.javaex.user.UserModel;
import com.example.javaex.user.UserModelDetailsService;
import com.example.javaex.user.workout.WorkoutModelDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {

    private final UserModelDetailsService userModelDetailsService;
    private final WorkoutModelDetailsService workoutModelDetailsService;

    public AppController(UserModelDetailsService userModelDetailsService, WorkoutModelDetailsService workoutModelDetailsService) {
        this.userModelDetailsService = userModelDetailsService;
        this.workoutModelDetailsService = workoutModelDetailsService;
    }

    @GetMapping
    public List<UserModel> listAllUsers(){
        return userModelDetailsService.findAll();
    }

    @GetMapping("{id}")
    public UserModel findUserById(@PathVariable("id") Long id){
        return userModelDetailsService.findById(id);
    }

    @PostMapping()
    public UserModel saveNewUser(UserModel userModel){ return userModelDetailsService.save(userModel);}

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable("id")Long id){ userModelDetailsService.delete(id);}

    @PutMapping("{id}")
    public void updateUserById(@PathVariable("id")Long id, @RequestBody UserModel userModel){
        userModelDetailsService.updateUser(id,userModel);
    }
}
