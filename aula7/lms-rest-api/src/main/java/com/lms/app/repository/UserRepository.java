package com.lms.app.repository;

import com.lms.app.model.Role;
import com.lms.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repositório para a entidade User.
 * O @RepositoryRestResource expõe este repositório como um endpoint REST.
 * Ex: GET /api/users
 */
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNameContainingIgnoreCase(@Param("name") String name);

    List<User> findByRole(@Param("role") Role role);

    User findByEmail(@Param("email") String email);

}
