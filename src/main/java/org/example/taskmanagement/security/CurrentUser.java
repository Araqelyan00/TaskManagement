package org.example.taskmanagement.security;

import org.example.taskmanagement.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;


public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
    }
    public User getUser() {
        return user;
    }
}
