package com.vineela.classregistrationsystem.controller;

import com.vineela.classregistrationsystem.exception.ResourceNotFoundException;
import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.service.ClassService;
import com.vineela.classregistrationsystem.dto.ClassDTO;
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

/**
 * @author Vineela Boddula
 */

@Api(tags="Class", value="Class Registration System", description="Operations pertaining to class in Class Registration System")
@RestController
@RequestMapping("/api/v1")
public class ClassController {

    @Autowired
    private ClassService classService;

    // Find All
    @ApiOperation(value = "View a list of available classes", response = ClassDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/classes", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('STUDENT') or hasRole('ADMIN')")
    ResponseEntity<List<ClassDTO>> getAllClasses() {
        List<ClassDTO> classDTOList = classService.getClasses();
        return ResponseEntity.ok().body(classDTOList);
    }

    // Save
    @ApiOperation(value = "Add a class")
    @PostMapping(value = "/classes", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> createClass(@ApiParam(value = "Class object store in database table", required = true) @Valid @RequestBody Class newClass) {
        classService.saveClass(newClass);
        return ResponseEntity.ok().build();
    }

    // Find by id
    @ApiOperation(value = "Get a class by Id", response = ClassDTO.class)
    @GetMapping(value = "/classes/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('STUDENT') or hasRole('ADMIN')")
    ResponseEntity<ClassDTO> getClassById(@ApiParam(value = "Class id from which class object will retrieve", required = true) @PathVariable @Min(1) long id)  throws ResourceNotFoundException {
        ClassDTO c = classService.getClass(id);
        return ResponseEntity.ok().body(c);
    }

    // Update by id
    @ApiOperation(value = "Update a class")
    @PutMapping(value = "/classes/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Class> updateClass(@ApiParam(value = "Class Id to update class object", required = true) @PathVariable long id,
                                      @ApiParam(value = "Update class object", required = true) @Valid @RequestBody Class updateClass) throws ResourceNotFoundException {
        Class updatedClass = classService.updateClass(id, updateClass);
        return ResponseEntity.ok().body(updatedClass);
    }

    // Delete by id
    @ApiOperation(value = "Delete a class")
    @DeleteMapping(value = "/classes/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteClass(@ApiParam(value = "Class Id from which class object will delete from database table", required = true) @PathVariable long id) throws ResourceNotFoundException {
        classService.deleteClass(id);
        return ResponseEntity.ok().build();
    }
}
