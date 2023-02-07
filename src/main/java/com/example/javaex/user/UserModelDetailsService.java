package com.example.javaex.user;

import com.example.javaex.user.dao.UserModelDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserModelDetailsService {

    private final UserModelDAO userModelDAO;

    public UserModel findUser(String name){
        return userModelDAO.findUser(name);
    }

    public void save(UserModel userModel){
    userModelDAO.save(userModel);
    }

    public void delete(Long id){
        userModelDAO.delete(id);
    }

    public List<UserModel> findAll(){
        return userModelDAO.findAll();
    }

    public UserModel findById(Long id){
        return userModelDAO.findById(id);
    }
}
