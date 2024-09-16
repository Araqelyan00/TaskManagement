package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.entity.User;
import org.example.taskmanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/user")
    public String getUserPage(Model model) {
        //TODO
        return "user";
    }

    @GetMapping("/users")
    public String getAllUsers(ModelMap modelMap) {
        List<User> users = userService.findAllUsers();
        modelMap.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/add")
    public String addUserPage(ModelMap modelMap) {
        modelMap.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user, @RequestParam("file") MultipartFile file, ModelMap modelMap) {
        try {
            userService.saveUser(user, file);
            return "redirect:/users";
        } catch (IOException e) {
            modelMap.addAttribute("error", "Error saving user: " + e.getMessage());
            return "addUser";
        }
    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/user/getImage")
    public String getUserImage(@RequestParam("fileName") String fileName, ModelMap modelMap) {

        //TODO
            return "userImage";

    }
}
