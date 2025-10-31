package com.lms.app;

import com.lms.app.model.Course;
import com.lms.app.model.Role;
import com.lms.app.model.User;
import com.lms.app.repository.CourseRepository;
import com.lms.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

/**
 * Classe principal da aplicação Spring Boot LMS.
 * Implementa CommandLineRunner para popular o banco de dados H2 ao iniciar.
 */
@SpringBootApplication
public class LmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsApplication.class, args);
    }

    // Bean para popular alguns dados de teste no H2 ao iniciar
    @Bean
    public CommandLineRunner demoData(UserRepository userRepository, CourseRepository courseRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // --- INÍCIO DA SOLUÇÃO DE SEGURANÇA PARA INICIALIZAÇÃO ---
            // 1. Cria um contexto de segurança temporário com um usuário ADMIN
            // Isso permite que chamadas a repositórios protegidos (como deleteAll() e save()) sejam executadas.
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    "startup_user",
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            try {
                // EXCLUSÃO (Requer ROLE_ADMIN)
                courseRepository.deleteAll();
                userRepository.deleteAll();

                // --- Criptografando Senhas ---
                String adminPass = passwordEncoder.encode("admin123");
                String profPass = passwordEncoder.encode("prof123");
                String studentPassA = passwordEncoder.encode("aluno123");
                String studentPassB = passwordEncoder.encode("aluna123");

                // 1. Criar Usuários com senhas criptografadas (Requer ROLE_ADMIN)
                User admin = new User("Admin Master", "admin@lms.com", Role.ADMIN, adminPass);
                User prof1 = new User("Dr. Maria Silva", "maria@lms.com", Role.TEACHER, profPass);
                User studentA = new User("João Aluno", "joao@lms.com", Role.STUDENT, studentPassA);
                User studentB = new User("Ana Aluna", "ana@lms.com", Role.STUDENT, studentPassB);

                userRepository.save(admin);
                userRepository.save(prof1);
                userRepository.save(studentA);
                userRepository.save(studentB);
                
                // 2. Criar Cursos (Requer ROLE_ADMIN ou ROLE_TEACHER)
                Course course1 = new Course("Introdução ao Spring Boot", "Curso básico de desenvolvimento de APIs.", prof1);
                Course course2 = new Course("JPA Avançado", "Tópicos complexos de persistência com Hibernate.", prof1);
                
                // 3. Matricular Alunos
                course1.addStudent(studentA);
                course1.addStudent(studentB);
                
                course2.addStudent(studentA);

                courseRepository.save(course1);
                courseRepository.save(course2);

            } finally {
                // 2. Limpa o contexto de segurança após a inicialização, restaurando o estado normal.
                SecurityContextHolder.clearContext();
            }
            // --- FIM DA SOLUÇÃO DE SEGURANÇA PARA INICIALIZAÇÃO ---

            
            System.out.println("-----------------------------------------------------");
            System.out.println("LMS Data Initialized.");
            System.out.println("Admin (ROLE_ADMIN): admin@lms.com / admin123");
            System.out.println("Teacher (ROLE_TEACHER): maria@lms.com / prof123");
            System.out.println("Student A (ROLE_STUDENT): joao@lms.com / aluno123");
            System.out.println("-----------------------------------------------------");
            System.out.println("Aplicação LMS iniciada. Endpoints disponíveis em /api/");
            System.out.println("Usuários: http://localhost:8080/api/users");
            System.out.println("Cursos: http://localhost:8080/api/courses");
            System.out.println("-----------------------------------------------------");
        };
    }
}
