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
    public CommandLineRunner demoData(UserRepository userRepository, CourseRepository courseRepository) {
        return args -> {

            courseRepository.deleteAll();
            userRepository.deleteAll();

            // 1. Criar Usuários
            User admin = new User("Admin Master", "admin@lms.com", Role.ADMIN);
            User prof1 = new User("Dr. Maria Silva", "maria@lms.com", Role.TEACHER);
            User studentA = new User("João Aluno", "joao@lms.com", Role.STUDENT);
            User studentB = new User("Ana Aluna", "ana@lms.com", Role.STUDENT);

            userRepository.save(admin);
            userRepository.save(prof1);
            userRepository.save(studentA);
            userRepository.save(studentB);
            
            // 2. Criar Cursos
            Course course1 = new Course("Introdução ao Spring Boot", "Curso básico de desenvolvimento de APIs.", prof1);
            Course course2 = new Course("JPA Avançado", "Tópicos complexos de persistência com Hibernate.", prof1);
            
            // 3. Matricular Alunos
            course1.addStudent(studentA);
            course1.addStudent(studentB);
            
            course2.addStudent(studentA);

            courseRepository.save(course1);
            courseRepository.save(course2);
            
            System.out.println("-----------------------------------------------------");
            System.out.println("Aplicação LMS iniciada. Endpoints disponíveis em /api/");
            System.out.println("Usuários: http://localhost:8080/api/users");
            System.out.println("Cursos: http://localhost:8080/api/courses");
            System.out.println("-----------------------------------------------------");
        };
    }
}