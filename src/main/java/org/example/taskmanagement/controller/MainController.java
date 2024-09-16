package org.example.taskmanagement.controller;

import org.example.taskmanagement.entity.User;
import org.example.taskmanagement.entity.UserType;
import org.example.taskmanagement.security.CurrentUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccessPage(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            User user = currentUser.getUser();
            if (user.getRole().equals(UserType.MANAGER)){
                return "redirect:/manager";
            } else if (user.getRole().equals(UserType.USER)) {
                return "redirect:/user";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/accessDenied")
    public String accessDeniedPage() {
        return "accessDenied";
    }

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(value = "error", required = false) String error, ModelMap modelMap) {
        if (error != null && error.equals("true")) {
            modelMap.addAttribute("error", "true");
        }
        return "loginPage";
    }
}
