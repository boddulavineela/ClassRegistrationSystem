package com.vineela.classregistrationsystem.repository;

import com.vineela.classregistrationsystem.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Vineela Boddula
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    StudentRepository repository;

    @Test
    public void testForEmptyStudentRepository() {
        Iterable<Student> students = repository.findAll();
        assertThat(students).isEmpty();
    }

    @Test
    public void testCreateStudent() {
        Student student = repository.save(new Student(0, "Vineela", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh"));
        assertThat(student).hasFieldOrPropertyWithValue("firstName", "Vineela");
        assertThat(student).hasFieldOrPropertyWithValue("lastName", "Boddula");
    }

    @Test
    public void testDeleteStudentById() {
        Student student = new Student(0, "Vineela1", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(student);
        repository.deleteById(student.getId());
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void testGetAllStudents() {
        Student student1 = new Student(0, "Vineela1", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(student1);
        Student student2 = new Student(0, "Vineela2", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(student2);
        Student student3 = new Student(0, "Vineela3", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(student3);
        Iterable<Student> students = repository.findAll();
        assertThat(students).hasSize(3).contains(student1, student2, student3);
    }

    @Test
    public void testStudentById() {
        Student student1 = new Student(0, "Vineela", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(student1);
        Student student2 = new Student(0, "Vineela1", "Boddula1", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(student2);
        Optional<Student> foundStudent = repository.findById(student2.getId());
        assertThat(foundStudent.get()).isEqualTo(student2);
    }
}
