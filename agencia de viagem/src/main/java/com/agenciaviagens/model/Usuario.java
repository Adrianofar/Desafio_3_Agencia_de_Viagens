package com.agenciaviagens.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto increment
    private Long id;
    
    @NotBlank(message = "Username é obrigatório")
    @Column(unique = true, nullable = false)  // Não pode repetir username
    private String username;
    
    @NotBlank(message = "Senha é obrigatória")
    @Column(nullable = false)
    private String password;  // Vai ser criptografada
    
    @NotBlank(message = "Role é obrigatório")
    @Column(nullable = false)
    private String role;  // ROLE_ADMIN ou ROLE_USER
    
    @Column(nullable = false)
    private boolean enabled = true;  // Usuario ativo?
    
    // Construtores
    public Usuario() {
    }
    
    public Usuario(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = true;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}