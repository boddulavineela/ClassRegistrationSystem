package com.vineela.classregistrationsystem.repository;

import com.vineela.classregistrationsystem.model.Student;
import com.vineela.classregistrationsystem.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Vineela Boddula
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(nativeQuery = true, value = "SELECT s.id AS id, s.first_name AS firstName, s.last_name AS lastName, s.email_address AS emailAddress, s.phone_number AS phoneNumber, s.address AS address, GROUP_CONCAT(DISTINCT CONCAT(c.class_name) SEPARATOR '; ') as classesRegistered FROM students s left join student_class sc on sc.student_id = s.id left join classes c on sc.class_id = c.id group by s.id; ")
    List<StudentDTO> findAllStudents();

    @Query(nativeQuery = true, value = "SELECT s.id AS id, s.first_name AS firstName, s.last_name AS lastName, s.email_address AS emailAddress, s.phone_number AS phoneNumber, s.address AS address, GROUP_CONCAT(DISTINCT CONCAT(c.class_name) SEPARATOR '; ') as classesRegistered FROM students s left join student_class sc on sc.student_id = s.id left join classes c on sc.class_id = c.id where s.id = ?1 group by s.id")
    Optional<StudentDTO> findByStudentId(Long id);
}