package com.example.javaex.user.dao;

import com.example.javaex.user.UserModel;
import com.example.javaex.user.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public UserModel findUser(String name) {
        return userModelRepository.findByName(name);
    }

    @Override
    public void save(UserModel userModel) {
        userModelRepository.save(userModel);
    }

    @Override
    public void delete(Long id) {
        userModelRepository.deleteById(id);
    }

    @Override
    public List<UserModel> findAll() {
        return userModelRepository.findAll();
    }

    @Override
    public UserModel findById(Long id) {
        Optional<UserModel> result = userModelRepository.findById(id);

        UserModel foundUser;

        if(result.isPresent()){
            foundUser=result.get();
        }else{
            throw new RuntimeException("Did not find id: " + id);
        }
        return foundUser;
    }
}
