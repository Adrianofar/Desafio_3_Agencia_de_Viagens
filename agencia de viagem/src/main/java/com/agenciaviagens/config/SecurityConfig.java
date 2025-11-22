package com.agenciaviagens.config;

import com.agenciaviagens.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    // Criptografador de senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // Provedor de autenticação
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
    // Configuração de segurança (a parte mais importante!)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desabilita CSRF (só pra teste)
            .authorizeHttpRequests(auth -> auth
                // Qualquer um pode listar e pesquisar (GET)
                .requestMatchers(HttpMethod.GET, "/api/destinos/**").permitAll()
                
                // Avaliar precisa estar logado (USER ou ADMIN)
                .requestMatchers(HttpMethod.PATCH, "/api/destinos/*/avaliar")
                    .hasAnyRole("USER", "ADMIN")
                
                // Criar, atualizar e deletar só ADMIN
                .requestMatchers(HttpMethod.POST, "/api/destinos").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/destinos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/destinos/**").hasRole("ADMIN")
                
                // Resto precisa estar autenticado
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {})  // Autenticação Basic (username e senha)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // API REST não guarda sessão
            );
        
        return http.build();
    }
}