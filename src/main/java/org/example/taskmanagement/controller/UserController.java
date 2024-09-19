package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.entity.User;
import org.example.taskmanagement.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public String userHome() {
        return "user";
    }

    @GetMapping("/users")
    public String allUsers(ModelMap modelMap) {
        List<User> users = userService.findAllUsers();
        modelMap.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/add")
    public String addUserPage() {
        return "addUser";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user, @RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {

            Optional<User> byEmail = userService.findByEmail(user.getEmail());
            if (byEmail.isPresent()) {
                modelMap.addAttribute("error", "This email already exists");
                return "addUser";
            }
            if (!file.isEmpty() && file.getSize() > 0) {
                if (file.getContentType() != null && !file.getContentType().contains("image")) {
                    modelMap.addAttribute("error", "Please choose only image");
                }
            }
            userService.saveUser(user, file);
            return "redirect:/users";

    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping(value = "/user/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getUserImage(@RequestParam("fileName") String fileName) throws IOException {
        return userService.getUserImage(fileName);
    }
}
