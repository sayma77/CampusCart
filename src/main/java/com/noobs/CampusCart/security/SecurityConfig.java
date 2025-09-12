package com.noobs.CampusCart.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // optional: enable for production
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/signup", "/signin", "/css/**", "/js/**", "/images/**", "/about",
                                "/contactus")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/signin") // where POST should go
                        .usernameParameter("email") // match form input name
                        .passwordParameter("password")
                        .defaultSuccessUrl("/marketplace", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/signout")
                        .logoutSuccessUrl("/signin?signout")
                        .invalidateHttpSession(true) // Clear session
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403") // custom forbidden page
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
