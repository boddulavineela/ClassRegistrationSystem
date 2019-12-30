package com.vineela.classregistrationsystem.repository;

import com.vineela.classregistrationsystem.model.Professor;
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
public class ProfessorRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ProfessorRepository repository;

    @Test
    public void testForEmptyProfessorRepository() {
        Iterable<Professor> professors = repository.findAll();
        assertThat(professors).isEmpty();
    }

    @Test
    public void testCreateProfessor() {
        Professor professor = repository.save(new Professor(0, "Vineela", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh"));
        assertThat(professor).hasFieldOrPropertyWithValue("firstName", "Vineela");
        assertThat(professor).hasFieldOrPropertyWithValue("lastName", "Boddula");
    }

    @Test
    public void testDeleteProfessorById() {
        Professor professor = new Professor(0, "Vineela1", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(professor);
        repository.deleteById(professor.getId());
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void testGetAllProfessors() {
        Professor professor1 = new Professor(0, "Vineela1", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(professor1);
        Professor professor2 = new Professor(0, "Vineela2", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(professor2);
        Professor professor3 = new Professor(0, "Vineela3", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(professor3);
        Iterable<Professor> professors = repository.findAll();
        assertThat(professors).hasSize(3).contains(professor1, professor2, professor3);
    }

    @Test
    public void testProfessorById() {
        Professor professor1 = new Professor(0, "Vineela", "Boddula", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(professor1);
        Professor professor2 = new Professor(0, "Vineela1", "Boddula1", "boddulavineela@gmail.com", "111-222-3333", "Raleigh");
        entityManager.persist(professor2);
        Optional<Professor> foundProfessor = repository.findById(professor2.getId());
        assertThat(foundProfessor.get()).isEqualTo(professor2);
    }
}
