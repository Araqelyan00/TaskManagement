package org.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.example.taskmanagement.entity.Task;
import org.example.taskmanagement.entity.User;
import org.example.taskmanagement.entity.UserType;
import org.example.taskmanagement.repository.TaskRepository;
import org.example.taskmanagement.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public void saveNewTask(Task task) {
        if (task.getUser() != null && task.getUser().getId() != 0) {
            task.setUser(null);
        }
        taskRepository.save(task);
    }

    public Page<Task> findTasksByUserRole(User user, Pageable pageable) {
        return user.getRole().equals(UserType.USER) ? taskRepository.findAllByUserId(user.getId(),pageable) : taskRepository.findAll(pageable);
    }

    public void changeUserTask(int userId, int taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (taskOptional.isPresent() && userOptional.isPresent()) {
            Task task = taskOptional.get();
            User user = userOptional.get();
            if(task.getUser() != user) {
                task.setUser(user);
                taskRepository.save(task);
            }else if (taskOptional.isPresent() && userId == 0){
                taskOptional.get().setUser(null);
                taskRepository.save(taskOptional.get());
            }
        }
    }
}
