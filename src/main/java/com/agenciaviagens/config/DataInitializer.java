package com.agenciaviagens.config;

import com.agenciaviagens.model.Usuario;
import com.agenciaviagens.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Cria usuario ADMIN se não existir
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));  // Criptografa a senha
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            usuarioRepository.save(admin);
            System.out.println("✅ Usuário ADMIN criado!");
        }
        
        // Cria usuario USER se não existir
        if (!usuarioRepository.existsByUsername("user")) {
            Usuario user = new Usuario();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            usuarioRepository.save(user);
            System.out.println("✅ Usuário USER criado!");
        }
        
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     CREDENCIAIS PARA TESTE             ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ ADMIN (pode tudo):                     ║");
        System.out.println("║   Username: admin                      ║");
        System.out.println("║   Password: admin123                   ║");
        System.out.println("║                                        ║");
        System.out.println("║ USER (só pode avaliar):                ║");
        System.out.println("║   Username: user                       ║");
        System.out.println("║   Password: user123                    ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }
}