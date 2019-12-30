package com.vineela.classregistrationsystem.service;

import com.vineela.classregistrationsystem.exception.ResourceNotFoundException;
import com.vineela.classregistrationsystem.model.Class;
import com.vineela.classregistrationsystem.dto.ClassDTO;

import java.util.List;

/**
 * @author Vineela Boddula
 */
public interface ClassService {

    List<ClassDTO> getClasses();

    Class saveClass(Class theClass);

    Class updateClass(Long id, Class updateClass) throws ResourceNotFoundException;

    ClassDTO getClass(Long id) throws ResourceNotFoundException;

    void deleteClass(Long id) throws ResourceNotFoundException;
}
