package com.vineela.classregistrationsystem.repository;

import com.vineela.classregistrationsystem.model.Professor;
import com.vineela.classregistrationsystem.dto.ProfessorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * @author Vineela Boddula
 */
@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query(nativeQuery = true, value = "SELECT prof.id AS id, prof.first_name AS firstName, prof.last_name AS lastName, prof.email_address AS emailAddress, prof.phone_number AS phoneNumber, prof.office_address AS officeAddress, GROUP_CONCAT(DISTINCT CONCAT(c.class_name) SEPARATOR '; ') as classesTeaching FROM professors prof left join professor_class p on p.professor_id = prof.id left join classes c on p.class_id = c.id group by prof.id; ")
    List<ProfessorDTO> findAllProfessors();

    @Query(nativeQuery = true, value = "SELECT prof.id AS id, prof.first_name AS firstName, prof.last_name AS lastName, prof.email_address AS emailAddress, prof.phone_number AS phoneNumber, prof.office_address AS officeAddress, GROUP_CONCAT(DISTINCT CONCAT(c.class_name) SEPARATOR '; ') as classesTeaching FROM professors prof left join professor_class p on p.professor_id = prof.id left join classes c on p.class_id = c.id where prof.id = ?1 group by prof.id")
    Optional<ProfessorDTO> findByProfessorId(Long id);
}