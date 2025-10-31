package com.lms.app.repository;

import com.lms.app.model.Course;
import com.lms.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Repositório para a entidade Course.
 * O @RepositoryRestResource expõe este repositório como um endpoint REST.
 * Ex: GET /api/courses
 */
@RepositoryRestResource(collectionResourceRel = "courses", path = "courses")
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTitleContainingIgnoreCase(@Param("title") String title);

    List<Course> findByTeacher(@Param("teacher") User teacher);
}
