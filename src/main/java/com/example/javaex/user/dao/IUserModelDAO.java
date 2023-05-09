package com.example.javaex.user.dao;

import com.example.javaex.user.UserModel;


import java.util.List;

public interface IUserModelDAO<T> {

    UserModel findByUsername(String username);
    void save(UserModel userModel);
    void delete(Long id);
    List<UserModel> findAll();
    UserModel findById(Long id);
    void updateUser(Long id, UserModel userModel);
}
