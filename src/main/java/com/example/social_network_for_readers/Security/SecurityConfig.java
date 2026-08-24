/*package com.example.social_network_for_readers.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(httpForm -> httpForm
                        .loginPage("/login")
                        .permitAll()
                )
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/register","/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

}
*/