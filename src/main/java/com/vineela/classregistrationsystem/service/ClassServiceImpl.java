package com.vineela.classregistrationsystem.service;

import com.vineela.classregistrationsystem.dto.ClassDTO;
import com.vineela.classregistrationsystem.exception.ResourceNotFoundException;
import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author Vineela Boddula
 */
@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepository;

    @Override
    @Transactional
    public List<ClassDTO> getClasses() {
        return classRepository.findAllClasses();
    }

    @Override
    @Transactional
    public Class saveClass(Class theClass) {
        return classRepository.save(theClass);
    }

    @Override
    @Transactional
    public ClassDTO getClass(Long id) throws ResourceNotFoundException {
        return classRepository.findByClassId(id).orElseThrow(
                () -> new ResourceNotFoundException("Class not found for this id :: " + id));
    }

    @Override
    @Transactional
    public Class updateClass(Long id, Class updateClass) throws ResourceNotFoundException {
        Class c = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found for this id :: " + id));
        c.setClassNumber(updateClass.getClassNumber());
        c.setClassName(updateClass.getClassName());
        c.setClassDescription(updateClass.getClassDescription());
        return classRepository.save(c);
    }

    @Override
    @Transactional
    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }
}
