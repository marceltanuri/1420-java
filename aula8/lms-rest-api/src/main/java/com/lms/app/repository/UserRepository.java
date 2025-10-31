package com.lms.app.repository;

import com.lms.app.model.Role;
import com.lms.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para a entidade User. Não é mais exposto como um endpoint REST.
 * A segurança é agora aplicada na camada de Controller.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Método usado internamente pelo UserDetailsService, não precisa de @PreAuthorize.
    User findByEmail(@Param("email") String email);
    
    // Métodos de busca customizados (não serão usados pelo Data REST)
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);
    List<User> findByRole(@Param("role") Role role);
}
