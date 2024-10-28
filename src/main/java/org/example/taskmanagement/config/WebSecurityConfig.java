package org.example.taskmanagement.config;

import org.example.taskmanagement.entity.UserType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin().loginPage("/loginPage").loginProcessingUrl("/login")
                .failureUrl("/loginPage?error=true")
                .defaultSuccessUrl("/loginSuccess")
                .and().logout().logoutSuccessUrl("/")
                .and().authorizeRequests()
                .antMatchers("/tasks/add").hasAuthority(UserType.MANAGER.name())
                .antMatchers("/tasks").authenticated()
                .antMatchers("/users").hasAuthority(UserType.MANAGER.name())
                .antMatchers("/users/delete").hasAuthority(UserType.MANAGER.name())
                .antMatchers("/manager").hasAuthority(UserType.MANAGER.name())
                .antMatchers("/user").hasAuthority(UserType.USER.name())
                .anyRequest().permitAll()
                .and().exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
