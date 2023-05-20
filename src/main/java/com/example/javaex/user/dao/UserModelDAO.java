package com.example.javaex.user.dao;

import com.example.javaex.exceptionhandler.UserNotFoundException;
import com.example.javaex.user.UserModel;
import com.example.javaex.user.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UserModelDAO implements IUserModelDAO<UserModel>{

    private final UserModelRepository userModelRepository;

    @Autowired
    public UserModelDAO(UserModelRepository userModelRepository){
        this.userModelRepository = userModelRepository;
    }


    @Override
    public UserModel findUser(String username) {
        return userModelRepository.findByUsername(username);
    }

    @Override
    public void save(UserModel userModel) {

        userModelRepository.save(userModel);
    }

    @Override
    public void delete(Long id) {
        try{
            userModelRepository.deleteById(id);
        }catch (Exception e){
            throw new UserNotFoundException("UserModel id not found: "+id);
        }

    }

    @Override
    public List<UserModel> findAll() {
        if (userModelRepository.findAll().size()<=0){
            throw new UserNotFoundException("No UserModels found");
        }
        return userModelRepository.findAll();
    }

    @Override
    public UserModel findById(Long id) {
        Optional<UserModel> result = userModelRepository.findById(id);

        UserModel foundUser;

        if(result.isPresent()){
            foundUser=result.get();
        }else{
            throw new UserNotFoundException("Did not find id: " + id);
        }
        return foundUser;
    }

    @Transactional
    public void updateUser(UserModel userModel){


        userModelRepository.save(userModel);

    }

    @Override
    public void deleteAll() {
        userModelRepository.deleteAll();
    }

    public List<UserModel> findByHasRegisteredWorkouts(boolean hasWorkouts) {

        return userModelRepository.findByHasRegisteredWorkouts(hasWorkouts);
    }

    public List<UserModel> findByNameContaining(String name) {
        return userModelRepository.findByNameContaining(name);
    }
}
