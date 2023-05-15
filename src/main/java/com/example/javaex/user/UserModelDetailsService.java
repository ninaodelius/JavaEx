package com.example.javaex.user;

import com.example.javaex.config.AppPasswordConfig;
import com.example.javaex.user.auth.UserRoles;
import com.example.javaex.user.dao.UserModelDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserModelDetailsService implements UserDetailsService {

    private final UserModelDAO userModelDAO;
    private final AppPasswordConfig appPasswordConfig;

    @Autowired
    public UserModelDetailsService(UserModelDAO userModelDAO, AppPasswordConfig appPasswordConfig) {
        this.userModelDAO = userModelDAO;
        this.appPasswordConfig = appPasswordConfig;
    }



    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userModelDAO.findUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }



    //public UserModel findUser(String name){
    //    return userModelDAO.findByUsername(name);
    //}

    public UserModel save(UserModel userModel){
        userModel.setPassword(appPasswordConfig.bCryptPassword().encode(userModel.getPassword()));
        userModel.setAccountNonExpired(true);
        userModel.setAccountNonLocked(true);
        userModel.setCredentialsNonExpired(true);
        userModel.setEnabled(true);

        //ta bort nedan
        userModel.setAuthorities(UserRoles.USER.getGrantedAuthorities());


        /*
        String role = String.valueOf(userModel.getAuthorities().iterator().next());

        switch (role) {
            case "ADMIN" ->  userModel.setAuthorities(UserRoles.ADMIN.getGrantedAuthorities());
            case "FLASH" -> userModel.setAuthorities(UserRoles.USER.getGrantedAuthorities());
        }*/

    userModelDAO.save(userModel);
        return userModel;
    }

    public void deleteById(Long id){
        userModelDAO.delete(id);
    }

    public List<UserModel> findAll(){
        return userModelDAO.findAll();
    }

    public UserModel findById(Long id){
        return userModelDAO.findById(id);
    }



    public void updateUser(Long id, UserModel userModel){
        userModelDAO.updateUser(id,userModel);
    }

    public List<UserModel> findByNameContaining(String name) {
        return userModelDAO.findByNameContaining(name);
    }

    public void deleteAll() {
        userModelDAO.deleteAll();
    }

    public List<UserModel> findByHasRegisteredWorkouts(boolean b) {
        return userModelDAO.findByHasRegisteredWorkouts(b);
    }
}
