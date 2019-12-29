package com.vineela.classregistrationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vineela Boddula
 */
@Entity
@Table(name = "classes")
@ApiModel(description = "All details about the Class. ")
public class Class {

    @ApiModelProperty(notes = "The database generated Class ID")
    private long id;
    @ApiModelProperty(notes = "The Class Number", required = true)
    private String classNumber;
    @ApiModelProperty(notes = "The Class Name", required = true)
    private String className;
    @ApiModelProperty(notes = "The Class Description")
    private String classDescription;
    private Set<Professor> professors = new HashSet<>();
    private Set<Student> students = new HashSet<>();

    public Class() {
    }

    public Class(Long id, String classNumber, String className, String classDescription) {
        this.id = id;
        this.classNumber = classNumber;
        this.className = className;
        this.classDescription = classDescription;
    }

    public Class(Long id, String classNumber, String className, String classDescription, Set<Professor> professors, Set<Student> students) {
        this.id = id;
        this.classNumber = classNumber;
        this.className = className;
        this.classDescription = classDescription;
        this.professors = professors;
        this.students = students;
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
    @Size(min = 3, max = 50)
    @Column(name = "class_number", nullable = false)
    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "class_name", nullable = false)
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    @ManyToMany(mappedBy = "classes")
    @JsonIgnore
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @ManyToMany(mappedBy = "classes")
    @JsonIgnore
    public Set<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }

    @Override
    public String toString() {
        return "Class [id=" + id + ", classNumber=" + classNumber + ", className=" + className + ", classDescription=" + classDescription
                + "]";
    }
}