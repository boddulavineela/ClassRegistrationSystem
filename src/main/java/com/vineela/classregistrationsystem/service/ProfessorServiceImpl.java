package com.vineela.classregistrationsystem.service;

import com.vineela.classregistrationsystem.exception.ResourceNotFoundException;
import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.model.Professor;
import com.vineela.classregistrationsystem.repository.ProfessorRepository;
import com.vineela.classregistrationsystem.dto.ProfessorDTO;
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
public class ProfessorServiceImpl implements ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    @Transactional
    public List<ProfessorDTO> getProfessors() {
        return professorRepository.findAllProfessors();
    }

    @Override
    @Transactional
    public Professor saveProfessor(Professor theProfessor) {
        return professorRepository.save(theProfessor);
    }

    @Override
    @Transactional
    public ProfessorDTO getProfessor(Long id) throws ResourceNotFoundException {
        return professorRepository.findByProfessorId(id).orElseThrow(
                () -> new ResourceNotFoundException("Professor not found for this id :: " + id));
    }

    @Override
    @Transactional
    public Professor updateProfessor(Long id, Professor updateProfessor) throws ResourceNotFoundException {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Professor not found for this id :: " + id));
        professor.setFirstName(updateProfessor.getFirstName());
        professor.setLastName(updateProfessor.getLastName());
        professor.setEmailAddress(updateProfessor.getEmailAddress());
        professor.setPhoneNumber(updateProfessor.getPhoneNumber());
        professor.setOfficeAddress(updateProfessor.getOfficeAddress());
        return professorRepository.save(professor);
    }

    @Override
    @Transactional
    public Professor assignClass(Long id, Class theClass) throws ResourceNotFoundException {
        Optional<Professor> professor = professorRepository.findById(id);
        if (professor.isPresent()) {
            Set<Professor> professors = new HashSet<>();
            professors.add(professor.get());
            theClass.setProfessors(professors);
        }
        return professor.map(x -> {
            Set<Class> newClasses = new HashSet<>();
            newClasses.add(theClass);
            x.getClasses().addAll(newClasses);
            return professorRepository.save(x);
        }).orElseThrow(() -> new ResourceNotFoundException("Professor not found for this id :: " + id));
    }

    @Override
    @Transactional
    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }
}
