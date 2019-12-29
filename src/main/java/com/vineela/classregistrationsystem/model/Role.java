package com.vineela.classregistrationsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NaturalId;

/**
 * @author Vineela Boddula
 */
@Entity
@Table(name = "roles")
@ApiModel(description = "All details about the Role. ")
public class Role {
    @ApiModelProperty(notes = "The database generated Role ID")
    private Long id;

    @ApiModelProperty(notes = "The Role Name", required = true)
    private RoleName name;

    public Role() {}

    public Role(RoleName name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}