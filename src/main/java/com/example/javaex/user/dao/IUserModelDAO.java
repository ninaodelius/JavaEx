package com.example.javaex.user.dao;

import com.example.javaex.user.UserModel;


import java.util.List;

public interface IUserModelDAO<T> {

    UserModel findUser(String name);
    UserModel save(UserModel userModel);
    void delete(Long id);
    List<UserModel> findAll();
    UserModel findById(Long id);
    void updateUser(Long id, UserModel userModel);
}
