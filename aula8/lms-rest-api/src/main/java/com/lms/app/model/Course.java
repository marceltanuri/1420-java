package com.lms.app.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entidade que representa um Curso.
 * Possui um Professor (User com Role.TEACHER) e uma coleção de Alunos (User com Role.STUDENT).
 */
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @Lob // Para descrições longas
    private String description;

    // Relacionamento com o Professor (Um curso tem um professor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private User teacher;

    // Relacionamento com os Alunos (Muitos cursos para muitos alunos)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "course_students",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    // O JsonIgnoreProperties é usado aqui para evitar recursão infinita 
    // (Curso -> Alunos -> Cursos -> ...) ao serializar para JSON.
    // Usamos 'courses' aqui pois Spring Data REST pode expor esse atributo no User.
    @JsonIgnoreProperties("enrolledCourses") 
    private Set<User> students = new HashSet<>();

    // Construtor, Getters e Setters

    public Course() {}

    public Course(String title, String description, User teacher) {
        this.title = title;
        this.description = description;
        this.teacher = teacher;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public void addStudent(User student) {
        this.students.add(student);
    }
}
