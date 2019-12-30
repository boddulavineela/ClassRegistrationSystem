package com.vineela.classregistrationsystem.service;

import com.vineela.classregistrationsystem.exception.ResourceNotFoundException;
import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.model.Professor;
import com.vineela.classregistrationsystem.dto.ProfessorDTO;

import java.util.List;

/**
 * @author Vineela Boddula
 */
public interface ProfessorService {

    List<ProfessorDTO> getProfessors();

    Professor saveProfessor(Professor theProfessor);

    Professor updateProfessor(Long id, Professor updateProfessor) throws ResourceNotFoundException;

    ProfessorDTO getProfessor(Long id) throws ResourceNotFoundException;

    Professor assignClass(Long id, Class theClass) throws ResourceNotFoundException;

    void deleteProfessor(Long id) throws ResourceNotFoundException;
}
