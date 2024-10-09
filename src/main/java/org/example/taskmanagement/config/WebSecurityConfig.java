package org.example.taskmanagement.config;

//import org.example.taskmanagement.entity.UserType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf()
//                .disable()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/success")
//                .failureUrl("/login?error=true")
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/tasks/add")
//                .hasAuthority(UserType.MANAGER.name())
//                .antMatchers("/tasks")
//                .authenticated()
//                .antMatchers("/users")
//                .hasAuthority(UserType.MANAGER.name())
//                .antMatchers("/users/delete")
//                .hasAuthority(UserType.MANAGER.name())
//                .antMatchers("/manager")
//                .hasAuthority(UserType.MANAGER.name())
//                .antMatchers("/user")
//                .hasAuthority(UserType.USER.name())
//                .anyRequest()
//                .permitAll()
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/accessDenied");
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
//}
import org.example.taskmanagement.entity.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

//    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
//        this.passwordEncoder = passwordEncoder;
//        this.userDetailsService = userDetailsService;
//    }


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

    //    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/manager")
//                        .failureUrl("/login?error=true")
//                )
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/")
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/tasks/add").hasAuthority(UserType.MANAGER.name())
//                        .requestMatchers("/tasks").authenticated()
//                        .requestMatchers("/users", "/users/delete", "/manager").hasAuthority(UserType.MANAGER.name())
//                        .requestMatchers("/user").hasAuthority(UserType.USER.name())
//                        .anyRequest().permitAll()
//                )
//                .exceptionHandling(exception -> exception
//                        .accessDeniedPage("/accessDenied")
//                );
//        return http.build();
//    }
}
