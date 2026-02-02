package com.reidosmotores.itam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize nos Controllers e sec:authorize no HTML
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. DESATIVA A PROTEÇÃO CSRF (Resolve o problema do login recarregar sem entrar)
            .csrf(csrf -> csrf.disable()) 
            
            .authorizeHttpRequests((requests) -> requests
                // Libera o acesso aos arquivos visuais (CSS, JS, Imagens) antes do login
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**", "/static/**").permitAll()
                // Todo o resto exige estar logado
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")           // Aponta para o seu Controller @GetMapping("/login")
                .defaultSuccessUrl("/", true)  // FORÇA ir para o Dashboard após logar
                .failureUrl("/login?error")    // Se errar a senha, volta com erro na URL
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    // Bean OBRIGATÓRIO para a senha funcionar
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}