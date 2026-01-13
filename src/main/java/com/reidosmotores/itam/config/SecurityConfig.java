package com.reidosmotores.itam.config;

import com.reidosmotores.itam.model.Usuario;
import com.reidosmotores.itam.repository.UsuarioRepository;
// Removemos as importações manuais complexas que estavam falhando
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Criptografador de Senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Configuração das Rotas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .defaultSuccessUrl("/ativos", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }

    // 3. Gerenciador de Autenticação (VERSÃO SIMPLIFICADA)
    // O Spring pega automaticamente nosso AutenticacaoService e o PasswordEncoder
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 4. Criação do Usuário Admin Inicial
    @Bean
    public CommandLineRunner criarUsuarioAdmin(UsuarioRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.count() == 0) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("123456"));
                admin.setRole("ADMIN");
                
                repository.save(admin);
                System.out.println(">>> USUÁRIO ADMIN CRIADO NO BANCO COM SUCESSO! <<<");
            }
        };
    }
}