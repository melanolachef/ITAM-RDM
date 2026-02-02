package com.reidosmotores.itam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.reidosmotores.itam.model.Usuario;
import com.reidosmotores.itam.repository.UsuarioRepository;

@SpringBootApplication
public class ItamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItamApplication.class, args);
    }

    // --- GERADOR DE ADMIN (Executa ao iniciar) ---
   @Bean
    public CommandLineRunner demo(UsuarioRepository repository, PasswordEncoder encoder) {
        return (args) -> {
            
            // 1. Tenta achar o admin antigo
            var adminExistente = repository.findByUsername("admin");
            
            // 2. Se existir, deleta ele para garantir que não tem lixo/senha velha
            if (adminExistente.isPresent()) {
                repository.delete(adminExistente.get());
                System.out.println(">>> ADMIN ANTIGO DELETADO PARA LIMPEZA <<<");
            }

            // 3. Cria o Admin do Zero (Garantido)
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("123456")); // Senha garantida
            admin.setRole("ADMIN");
            
            repository.save(admin);
            
            System.out.println("==================================================");
            System.out.println(">>> NOVO ADMIN CRIADO COM SUCESSO! <<<");
            System.out.println(">>> Usuário: admin");
            System.out.println(">>> Senha:   123456");
            System.out.println("==================================================");
        };
    }
}