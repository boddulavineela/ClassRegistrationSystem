package com.vineela.classregistrationsystem.service;

import com.vineela.classregistrationsystem.dto.StudentDTO;
import com.vineela.classregistrationsystem.exception.ResourceNotFoundException;
import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.model.Student;
import com.vineela.classregistrationsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Vineela Boddula
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public List<StudentDTO> getStudents() {
        return studentRepository.findAllStudents();
    }

    @Override
    @Transactional
    public void saveStudent(Student theStudent) {
        studentRepository.save(theStudent);
    }

    @Override
    @Transactional
    public StudentDTO getStudent(Long id) throws ResourceNotFoundException {
        return studentRepository.findByStudentId(id).orElseThrow(
                () -> new ResourceNotFoundException("Student not found for this id :: " + id));
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, Student updateStudent) throws ResourceNotFoundException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));
        student.setFirstName(updateStudent.getFirstName());
        student.setLastName(updateStudent.getLastName());
        student.setEmailAddress(updateStudent.getEmailAddress());
        student.setPhoneNumber(updateStudent.getPhoneNumber());
        student.setAddress(updateStudent.getAddress());
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student registerClass(Long id, Class theClass) throws ResourceNotFoundException {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Set<Student> students = new HashSet<>();
            students.add(student.get());
            theClass.setStudents(students);
        }
        return student.map(x -> {
            Set<Class> newClasses = new HashSet<>();
            newClasses.add(theClass);
            x.getClasses().addAll(newClasses);
            return studentRepository.save(x);
        }).orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + id));
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
