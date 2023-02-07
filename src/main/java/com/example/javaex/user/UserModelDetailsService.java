package com.example.javaex.user;

import com.example.javaex.user.dao.UserModelDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserModelDetailsService implements UserDetailsService {

    private final UserModelDAO userModelDAO;

    public UserModel findUser(String name){
        return userModelDAO.findUser(name);
    }

    public UserModel save(UserModel userModel){
    return userModelDAO.save(userModel);
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

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userModelDAO.findUser(name);
    }

    public void updateUser(Long id, UserModel userModel){
        userModelDAO.updateUser(id,userModel);
    }
}
