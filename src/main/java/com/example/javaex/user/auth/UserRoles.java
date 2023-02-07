package com.example.javaex.user.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.javaex.user.auth.UserPermissions.*;

public enum UserRoles {
    USER(Set.of(USER_READ)),
    ADMIN(Set.of(ADMIN_READ, ADMIN_WRITE));

    private final Set<UserPermissions> permissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermissions> getPermissions() {
        return permissions;
    }

    //create one role with both role and permissions
    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {

        //for loop with streams
        Set<SimpleGrantedAuthority> permissionsList = getPermissions().stream().map(
                index -> new SimpleGrantedAuthority(index.getUserPermission())
        ).collect(Collectors.toSet());

        //when finished: add role (ex. ROLE_ADMIN)
        permissionsList.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissionsList;
    }
}
