package org.example.taskmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.entity.Task;
import org.example.taskmanagement.entity.User;
import org.example.taskmanagement.security.CurrentUser;
import org.example.taskmanagement.service.TaskService;
import org.example.taskmanagement.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/tasks/add")
    public String addTaskPage(ModelMap modelMap) {
        List<User> users = userService.findAllUsers();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("task", new Task()); // Add this line to ensure the task object is passed to the view
        return "addTask";
    }

    @PostMapping("/tasks/add")
    public String addTask(@ModelAttribute Task task) {
        taskService.saveNewTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks")
    public String tasksPage(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Task> tasksByUserRole = taskService.findTasksByUserRole(currentUser.getUser(), PageRequest.of(currentPage - 1, pageSize));
        modelMap.addAttribute("tasks", tasksByUserRole);
        int totalPages = tasksByUserRole.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        modelMap.addAttribute("users", userService.findAllUsers());
        return "tasks";
    }

    @PostMapping("/task/changeUser")
    public String changeUser(@RequestParam("userId") int userId, @RequestParam("taskId") int taskId) {
        taskService.changeUserTask(taskId, userId);
        return "redirect:/tasks";
    }
}