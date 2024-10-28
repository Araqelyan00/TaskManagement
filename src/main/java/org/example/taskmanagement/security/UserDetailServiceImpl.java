package org.example.taskmanagement.security;

import org.example.taskmanagement.entity.User;
import org.example.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(userName);
        if (!byEmail.isPresent()) {
            throw new UsernameNotFoundException("Username doesn't exist");
        }
        return new CurrentUser(byEmail.get());
    }
}
