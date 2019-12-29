package com.vineela.classregistrationsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Vineela Boddula
 */
@ApiModel(description = "All details about the ProfessorDTO. ")
public interface ProfessorDTO {

    @ApiModelProperty(notes = "The database generated Professor ID", required = true)
    @Value("#{target.id}")
    long getId();

    @ApiModelProperty(notes = "The Professor First Name", required = true)
    @Value("#{target.firstName}")
    String getFirstName();

    @ApiModelProperty(notes = "The Professor Last Name", required = true)
    @Value("#{target.lastName}")
    String getLastName();

    @ApiModelProperty(notes = "The Professor Email Address", required = true)
    @Value("#{target.emailAddress}")
    String getEmailAddress();

    @ApiModelProperty(notes = "The Professor Phone Number")
    @Value("#{target.phoneNumber}")
    String getPhoneNumber();

    @ApiModelProperty(notes = "The Professor Office Address")
    @Value("#{target.officeAddress}")
    String getOfficeAddress();

    @ApiModelProperty(notes = "Classes Taught by the Professor (i.e Class Name with semicolon separated)")
    @Value("#{target.classesTeaching}")
    String getClassesTeaching();
}
