package com.vineela.classregistrationsystem.controller;

import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.model.Professor;
import com.vineela.classregistrationsystem.dto.ProfessorDTO;
import com.vineela.classregistrationsystem.exception.ResourceNotFoundException;
import com.vineela.classregistrationsystem.service.ProfessorService;
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
@Api(tags="Professor", value="Class Registration System", description="Operations pertaining to professor in Class Registration System")
@RestController
@RequestMapping("/api/v1")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    // Find All
    @ApiOperation(value = "View a list of available professors", response = ProfessorDTO.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/professors", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('STUDENT') or hasRole('ADMIN')")
    ResponseEntity<List<ProfessorDTO>> getAllProfessors() {
        List<ProfessorDTO> professorDTOList = professorService.getProfessors();
        return ResponseEntity.ok().body(professorDTOList);
    }

    // Save
    @ApiOperation(value = "Add a professor")
    @PostMapping(value = "/professors", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Professor> createProfessor(@ApiParam(value = "Professor object store in database table", required = true) @Valid @RequestBody Professor newProfessor) {
        Professor professor = professorService.saveProfessor(newProfessor);
        return ResponseEntity.ok().body(professor);
    }

    // Find by id
    @ApiOperation(value = "Get a professor by Id", response = ProfessorDTO.class)
    @GetMapping(value = "/professors/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('STUDENT') or hasRole('ADMIN')")
    ResponseEntity<ProfessorDTO> getProfessorById(@ApiParam(value = "Professor id from which professor object will retrieve", required = true) @PathVariable @Min(1) Long id) throws ResourceNotFoundException {
        ProfessorDTO professor = professorService.getProfessor(id);
        return ResponseEntity.ok().body(professor);
    }

    // Update by id
    @ApiOperation(value = "Update a professor")
    @PutMapping(value = "/professors/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    ResponseEntity<Professor> updateProfessor(@ApiParam(value = "Professor Id to update professor object", required = true) @PathVariable long id,
                                              @ApiParam(value = "Update professor object", required = true) @RequestBody Professor updateProfessor) throws ResourceNotFoundException {
        Professor updatedProfessor = professorService.updateProfessor(id, updateProfessor);
        return ResponseEntity.ok().body(updatedProfessor);
    }

    // Assign professor to a class
    @ApiOperation(value = "Assign professor to a class")
    @PutMapping(value = "/professors/{id}/assignClass", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PROFESSOR') or hasRole('ADMIN')")
    ResponseEntity<Professor> assignClass(@ApiParam(value = "Professor Id", required = true) @PathVariable Long id,
                                          @ApiParam(value = "Class assigned by professor object", required = true) @RequestBody Class theClass) throws ResourceNotFoundException {
        Professor updatedProfessor = professorService.assignClass(id, theClass);
        return ResponseEntity.ok().body(updatedProfessor);

    }

    // Delete by id
    @ApiOperation(value = "Delete a professor")
    @DeleteMapping(value = "/professors/{id}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteProfessor(@ApiParam(value = "Professor Id from which professor object will delete from database table", required = true) @PathVariable Long id) throws ResourceNotFoundException {
        professorService.deleteProfessor(id);
        return ResponseEntity.ok().build();
    }
}
