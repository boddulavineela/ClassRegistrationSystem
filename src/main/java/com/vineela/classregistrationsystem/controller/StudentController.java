package com.vineela.classregistrationsystem.controller;

import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.dto.StudentDTO;
import com.vineela.classregistrationsystem.exception.ResourceNotFoundException;
import com.vineela.classregistrationsystem.model.Student;
import com.vineela.classregistrationsystem.service.StudentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags="Student", value="Class Registration System", description="Operations pertaining to student in Class Registration System")
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Find All
    @ApiOperation(value = "View a list of available students", response = StudentDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/students", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('STUDENT') or hasRole('ADMIN')")
    ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> studentDTOList = studentService.getStudents();
        return ResponseEntity.ok().body(studentDTOList);
    }

    // Save
    @ApiOperation(value = "Add a student")
    @PostMapping(value = "/students", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Student>  createStudent(@ApiParam(value = "Student object store in database table", required = true) @Valid @RequestBody Student newStudent) {
        Student student = studentService.saveStudent(newStudent);
        return ResponseEntity.ok().body(student);
    }

    // Find by id
    @ApiOperation(value = "Get a professor by Id", response = StudentDTO.class)
    @GetMapping(value = "/students/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('STUDENT') or hasRole('ADMIN')")
    ResponseEntity<StudentDTO> getStudentById(@ApiParam(value = "Student id from which professor object will retrieve", required = true) @PathVariable @Min(1) Long id) throws ResourceNotFoundException {
        StudentDTO professor = studentService.getStudent(id);
        return ResponseEntity.ok().body(professor);
    }

    // Update by id
    @ApiOperation(value = "Update a student")
    @PutMapping(value = "/students/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    ResponseEntity<Student> updateStudent(@ApiParam(value = "Student Id to update student object", required = true) @PathVariable long id,
                         @ApiParam(value = "Update student object", required = true) @RequestBody Student updateStudent) throws ResourceNotFoundException {
        Student updatedStudent = studentService.updateStudent(id, updateStudent);
        return ResponseEntity.ok().body(updatedStudent);
    }

    // Student register to a class
    @ApiOperation(value = "Register student to a class")
    @PutMapping(value = "/students/{id}/registerClass", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    ResponseEntity<Student> registerClass(@ApiParam(value = "Student Id", required = true) @PathVariable Long id,
                          @ApiParam(value = "Class registered by student object", required = true) @RequestBody Class theClass) throws ResourceNotFoundException {
        Student updatedStudent = studentService.registerClass(id, theClass);
        return ResponseEntity.ok().body(updatedStudent);

    }

    // Delete by id
    @ApiOperation(value = "Delete a student")
    @DeleteMapping(value = "/students/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteStudent(@ApiParam(value = "Student Id from which student object will delete from database table", required = true) @PathVariable Long id) throws ResourceNotFoundException {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }
}
