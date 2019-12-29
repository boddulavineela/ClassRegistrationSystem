package com.vineela.classregistrationsystem.message.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

import javax.validation.constraints.*;
@ApiModel(description = "All details about the Signup. ")
public class SignUpRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    @ApiModelProperty(notes = "Name", required = true)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    @ApiModelProperty(notes = "User Name", required = true)
    private String username;

    @NotBlank
    @Size(max = 60)
    @Email
    @ApiModelProperty(notes = "Email", required = true)
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    @ApiModelProperty(notes = "Password", required = true)
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
    	return this.role;
    }
    
    public void setRole(Set<String> role) {
    	this.role = role;
    }
}