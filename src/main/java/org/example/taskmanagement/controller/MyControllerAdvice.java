package org.example.taskmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.taskmanagement.entity.User;
import org.example.taskmanagement.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MyControllerAdvice {

    @ModelAttribute(name = "currentUser")
    public User currentUser(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            return currentUser.getUser();
        }
        return null;
    }

    @ModelAttribute(name = "currentURL")
    public String currentURL(HttpServletRequest request) {
        return request.getRequestURI();
    }

}
