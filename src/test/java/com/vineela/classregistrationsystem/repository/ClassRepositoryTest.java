package com.vineela.classregistrationsystem.repository;

import com.vineela.classregistrationsystem.model.Class;
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
public class ClassRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ClassRepository repository;

    @Test
    public void testForEmptyClassRepository() {
        Iterable<Class> professors = repository.findAll();
        assertThat(professors).isEmpty();
    }

    @Test
    public void testCreateClass() {
        Class theClass = repository.save(new Class(0L, "CSC101", "JAVA", "Advanced Java"));
        assertThat(theClass).hasFieldOrPropertyWithValue("classNumber", "CSC101");
        assertThat(theClass).hasFieldOrPropertyWithValue("className", "JAVA");
    }

    @Test
    public void testDeleteClassById() {
        Class theClass = new Class(0L, "CSC101", "JAVA", "Advanced Java");
        entityManager.persist(theClass);
        repository.deleteById(theClass.getId());
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void testGetAllClasss() {
        Class class1 = new Class(0L, "CSC101", "JAVA 1", "Advanced Java 1");
        entityManager.persist(class1);
        Class class2 = new Class(0L, "CSC102", "JAVA 2", "Advanced Java 2");
        entityManager.persist(class2);
        Class class3 = new Class(0L, "CSC103", "JAVA 3", "Advanced Java 3");
        entityManager.persist(class3);
        Iterable<Class> professors = repository.findAll();
        assertThat(professors).hasSize(3).contains(class1, class2, class3);
    }

    @Test
    public void testClassById() {
        Class class1 = new Class(0L, "CSC101", "JAVA 1", "Advanced Java 1");
        entityManager.persist(class1);
        Class class2 = new Class(0L, "CSC102", "JAVA 2", "Advanced Java 2");
        entityManager.persist(class2);
        Optional<Class> foundClass = repository.findById(class2.getId());
        assertThat(foundClass.get()).isEqualTo(class2);
    }
}
