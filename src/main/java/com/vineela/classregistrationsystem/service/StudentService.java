package com.vineela.classregistrationsystem.service;

import com.vineela.classregistrationsystem.dto.StudentDTO;
import com.vineela.classregistrationsystem.exception.ResourceNotFoundException;
import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.model.Student;

import java.util.List;

/**
 * @author Vineela Boddula
 */
public interface StudentService {

    List<StudentDTO> getStudents();

    void saveStudent(Student theStudent);

    Student updateStudent(Long id, Student updateStudent) throws ResourceNotFoundException;

    StudentDTO getStudent(Long id) throws ResourceNotFoundException;

    Student registerClass(Long id, Class theClass) throws ResourceNotFoundException;

    void deleteStudent(Long id) throws ResourceNotFoundException;
}
