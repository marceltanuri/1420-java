package com.lms.app.controller;

import com.lms.app.model.Role;
import com.lms.app.model.User;
import com.lms.app.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar operações com Usuários.
 * A segurança é aplicada diretamente nos métodos, impedindo a exposição
 * insegura de métodos de repositório (como findByEmail) para a web.
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // GET /api/v1/users - Acessível por qualquer usuário autenticado
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public List<User> getAllUsers() {
        // Usa o método findAll do JpaRepository
        return userRepository.findAll();
    }

    // GET /api/v1/users/{id} - Apenas ADMIN pode ver qualquer ID, ou o próprio ID do usuário autenticado
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #id == @userRepository.findByEmail(authentication.name).id")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        // Note: findById retorna Optional, é mais robusto
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // --- MÉTODOS DE BUSCA CUSTOMIZADOS ---

    // GET /api/v1/users/search/byEmail?email={email}
    // Apenas ADMIN ou TEACHER podem buscar por email
    @GetMapping("/search/byEmail")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/v1/users/search/byRole?role={roleName}
    // Acessível por qualquer usuário autenticado
    @GetMapping("/search/byRole")
    @PreAuthorize("isAuthenticated()")
    public List<User> getUsersByRole(@RequestParam String role) {
        try {
            Role userRole = Role.valueOf(role.toUpperCase());
            return userRepository.findByRole(userRole);
        } catch (IllegalArgumentException e) {
            // Retorna uma lista vazia ou um erro 400 se o Role for inválido
            return List.of();
        }
    }

    // GET /api/v1/users/search/byName?name={name}
    // Acessível por qualquer usuário autenticado
    @GetMapping("/search/byName")
    @PreAuthorize("isAuthenticated()")
    public List<User> getUsersByNameContaining(@RequestParam String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
    
    // --- MÉTODOS DE MANIPULAÇÃO (POST, PUT, PATCH, DELETE) ---

    // POST /api/v1/users - Apenas ADMIN pode criar
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public User createUser(@RequestBody User user) {
        // Nota: A criptografia da senha deve ser feita no serviço/controller antes de salvar!
        // Neste exemplo simplificado, estamos salvando diretamente, mas em um projeto real, 
        // a senha deve ser codificada aqui antes de chamar save.
        return userRepository.save(user);
    }

    // PUT /api/v1/users/{id} - Apenas ADMIN pode atualizar qualquer ID, ou o próprio ID do usuário autenticado
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == @userRepository.findByEmail(authentication.name).id")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    // Atualiza os campos relevantes (código simplificado)
                    user.setName(userDetails.getName());
                    user.setEmail(userDetails.getEmail());
                    user.setRole(userDetails.getRole());
                    // A senha (senha) deve ser tratada separadamente e criptografada
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /api/v1/users/{id} - Apenas ADMIN pode atualizar parcialmente qualquer ID, ou o próprio ID do usuário autenticado
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == @userRepository.findByEmail(authentication.name).id")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User partialUser) {
        return userRepository.findById(id)
                .map(user -> {
                    // Atualiza o nome se fornecido
                    if (partialUser.getName() != null) {
                        user.setName(partialUser.getName());
                    }
                    // Atualiza o email se fornecido
                    if (partialUser.getEmail() != null) {
                        user.setEmail(partialUser.getEmail());
                    }
                    // Atualiza a role se fornecida
                    if (partialUser.getRole() != null) {
                        user.setRole(partialUser.getRole());
                    }
                    // Atualiza a senha se fornecida.
                    // **CRÍTICO:** Em um projeto real, você criptografaria a senha aqui (Ex: user.setSenha(passwordEncoder.encode(partialUser.getSenha())));
                    if (partialUser.getSenha() != null) {
                        user.setSenha(partialUser.getSenha()); 
                    }
                    
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/users/{id} - Apenas ADMIN pode deletar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
