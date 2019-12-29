package com.vineela.classregistrationsystem.repository;

import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.dto.ClassDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * @author Vineela Boddula
 */
@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    @Query(nativeQuery = true, value = "SELECT c.id AS id, c.class_number AS classNumber, c.class_name AS className, c.class_description AS classDescription, GROUP_CONCAT(DISTINCT CONCAT(prof.last_name , ', ', prof.first_name) SEPARATOR '; ') as professorAssigned, count(DISTINCT s.student_id) AS studentsCount FROM classes c left join student_class s on c.id = s.class_id left join professor_class p on p.class_id = c.id left join professors prof on prof.id = p.professor_id group by c.id;")
    List<ClassDTO> findAllClasses();

    @Query(nativeQuery = true, value = "SELECT c.id AS id, c.class_number AS classNumber, c.class_name AS className, c.class_description AS classDescription, GROUP_CONCAT(DISTINCT CONCAT(prof.last_name , ', ', prof.first_name) SEPARATOR '; ') as professorAssigned, count(DISTINCT s.student_id) AS studentsCount FROM classes c left join student_class s on c.id = s.class_id left join professor_class p on p.class_id = c.id left join professors prof on prof.id = p.professor_id where c.id = ?1 group by c.id")
    Optional<ClassDTO> findByClassId(Long id);
}