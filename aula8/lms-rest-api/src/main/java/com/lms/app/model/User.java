package com.lms.app.model;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Entidade que representa um Usuário no sistema.
 * Pode ser ADMIN, TEACHER (Professor) ou STUDENT (Aluno).
 */
@Entity
@Table(name = "lms_user") // Evita conflito com a palavra reservada 'user' em alguns bancos
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String senha;

    // Persiste o Enum como String no banco de dados
    @Enumerated(EnumType.STRING)
    private Role role;

    // Construtor, Getters e Setters (Omissos para brevidade, mas necessários em código real)
    
    public User() {}

    public User(String name, String email, Role role, String senha) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.senha = senha;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
