package com.vineela.classregistrationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author Vineela Boddula
 */
@Entity
@Table(name = "professors")
@ApiModel(description = "All details about the Professor. ")
public class Professor {

    @ApiModelProperty(notes = "The database generated Professor ID")
    private long id;

    @ApiModelProperty(notes = "The Professor First Name", required = true)
    private String firstName;

    @ApiModelProperty(notes = "The Professor Last Name", required = true)
    private String lastName;

    @ApiModelProperty(notes = "The Professor Email Address", required = true)
    private String emailAddress;

    @ApiModelProperty(notes = "The Professor Phone Number")
    private String phoneNumber;

    @ApiModelProperty(notes = "The Professor Office Address")
    private String officeAddress;
    private Set<Class> classes;

    public Professor() {
    }

    public Professor(long id, String firstName, String lastName, String emailAddress, String phoneNumber, String officeAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.officeAddress = officeAddress;
    }

    public Professor(long id, String firstName, String lastName, String emailAddress, String phoneNumber, String officeAddress, Set<Class> classes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.officeAddress = officeAddress;
        this.classes = classes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotBlank
    @Size(min=3, max = 50)
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotBlank
    @Size(min=3, max = 50)
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email_address", nullable = false)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "professor_class", joinColumns = @JoinColumn(name = "professor_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "class_id", referencedColumnName = "id"))
    public Set<Class> getClasses() {
        return classes;
    }

    public void setClasses(Set<Class> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Professor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailAddress=" + emailAddress + ", phoneNumber=" + phoneNumber + ", officeAddress=" + officeAddress
                + "]";
    }
}