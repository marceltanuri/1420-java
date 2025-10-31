package com.lms.app.security;

import com.lms.app.model.User;
import com.lms.app.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * Serviço customizado para carregar os detalhes do usuário
 * a partir do banco de dados (usando o UserRepository).
 * Usa o campo 'email' como o 'username' do Spring Security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // Injeção do UserRepository
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carrega o usuário pelo e-mail.
     * @param email O e-mail do usuário (usado como username).
     * @return Um objeto UserDetails do Spring Security.
     * @throws UsernameNotFoundException Se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário pelo email
        User user = userRepository.findByEmail(email);

        if (user == null) {
            // Lança exceção se o usuário não for encontrado
            throw new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email);
        }

        // Converte a Role para a GrantedAuthority do Spring Security.
        // O Spring Security espera que roles sejam prefixadas com "ROLE_", ex: "ROLE_ADMIN".
        Set<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        // Retorna um objeto UserDetails do Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),          // Username é o email
                user.getSenha(),          // Password é a senha criptografada (coluna 'senha')
                authorities               // Roles (ROLE_ADMIN, ROLE_TEACHER, etc.)
        );
    }
}
