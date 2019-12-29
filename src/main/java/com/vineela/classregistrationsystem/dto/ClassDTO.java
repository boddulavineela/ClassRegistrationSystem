package com.vineela.classregistrationsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Vineela Boddula
 */
@ApiModel(description = "All details about the ClassDTO. ")
public interface ClassDTO {

    @ApiModelProperty(notes = "The database generated Class ID", required = true)
    @Value("#{target.id}")
    long getId();

    @ApiModelProperty(notes = "The Class Number", required = true)
    @Value("#{target.classNumber}")
    String getClassNumber();

    @ApiModelProperty(notes = "The Class Name", required = true)
    @Value("#{target.className}")
    String getClassName();

    @ApiModelProperty(notes = "The Class Description")
    @Value("#{target.classDescription}")
    String getClassDescription();

    @ApiModelProperty(notes = "Professors assigned to the class (i.e Full Name with semicolon separated)")
    @Value("#{target.professorAssigned}")
    String getProfessorAssigned();

    @ApiModelProperty(notes = "Number of students registered to the class")
    @Value("#{target.studentsCount}")
    int getStudentsCount();
}
