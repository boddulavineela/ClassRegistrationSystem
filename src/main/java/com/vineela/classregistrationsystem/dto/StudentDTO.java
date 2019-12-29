package com.vineela.classregistrationsystem.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Vineela Boddula
 */
@ApiModel(description = "All details about the StudentDTO. ")
public interface StudentDTO {

    @ApiModelProperty(notes = "The database generated Student ID", required = true)
    @Value("#{target.id}")
    long getId();

    @ApiModelProperty(notes = "The Student First Name", required = true)
    @Value("#{target.firstName}")
    String getFirstName();

    @ApiModelProperty(notes = "The Student Last Name", required = true)
    @Value("#{target.lastName}")
    String getLastName();

    @ApiModelProperty(notes = "The Student Email Address", required = true)
    @Value("#{target.emailAddress}")
    String getEmailAddress();

    @ApiModelProperty(notes = "The Student Phone Number")
    @Value("#{target.phoneNumber}")
    String getPhoneNumber();

    @ApiModelProperty(notes = "The Student Address")
    @Value("#{target.address}")
    String getAddress();

    @ApiModelProperty(notes = "Classes Registered by the Student (i.e Class Name with semicolon separated)")
    @Value("#{target.classesRegistered}")
    String getClassesRegistered();
}
