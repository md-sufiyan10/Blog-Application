package com.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity   // Enables @PreAuthorize

public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       http
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(auth->auth
                       .requestMatchers("/api/users/register").permitAll()
                       .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("ADMIN", "USER")
                       .requestMatchers(HttpMethod.GET,"/api/users/**").hasAnyRole("ADMIN","USER")

                       // POST, PUT, DELETE - only ADMIN
                       .requestMatchers(HttpMethod.POST,"/api/users/**").hasAnyRole("ADMIN")
                       .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                       .requestMatchers(HttpMethod.DELETE,"/api/users/**").hasAnyRole("ADMIN")

                       // Enable Basic Authentication
                       .anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
               return http.build();
    }
    // Bean 3: In-memory users for testing (Temporary)

    @Bean
    public UserDetailsService userDetailsService() {
        // Create ADMIN user
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))  // encoded
                .roles("ADMIN")
                .build();

        // Create NORMAL user
        UserDetails user=User.builder()
                .username("USER")
                .password(passwordEncoder().encode("user123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin,user);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
