package com.example.javaex.user;

import com.example.javaex.config.AppPasswordConfig;
import com.example.javaex.user.auth.UserRoles;
import com.example.javaex.user.dao.UserModelDAO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserModelDetailsService implements UserDetailsService {

    private final UserModelDAO userModelDAO;
    private final AppPasswordConfig appPasswordConfig;

    public UserModelDetailsService(UserModelDAO userModelDAO, AppPasswordConfig appPasswordConfig) {
        this.userModelDAO = userModelDAO;
        this.appPasswordConfig = appPasswordConfig;
    }

    public UserModel findUser(String name){
        return userModelDAO.findByUsername(name);
    }

    public void save(UserModel userModel){
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userModelDAO.findByUsername(username);
    }

    public void updateUser(Long id, UserModel userModel){
        userModelDAO.updateUser(id,userModel);
    }
}
