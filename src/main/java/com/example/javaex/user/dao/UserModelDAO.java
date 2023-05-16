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
    public UserModel findUser(String username) {
        return userModelRepository.findByUsername(username);
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

    @Override
    public void updateUser(Long id, UserModel userModel){

        UserModel userToUpdate = findById(id);

        if (userModel.getName() != null) { userToUpdate.setName(userModel.getName()); }
        if (userModel.getUsername() != null) { userToUpdate.setUsername(userModel.getUsername()); }
        if (userModel.getPassword() != null) { userToUpdate.setPassword(userModel.getPassword()); }

        userToUpdate.setAccountNonExpired(true);
        userToUpdate.setAccountNonLocked(true);
        userToUpdate.setCredentialsNonExpired(true);
        userToUpdate.setEnabled(true);

        userModelRepository.save(userToUpdate);
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
