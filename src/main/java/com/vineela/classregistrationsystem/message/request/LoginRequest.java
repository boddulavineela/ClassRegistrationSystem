package com.vineela.classregistrationsystem.message.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(description = "All details about the Login. ")
public class LoginRequest {
    @NotBlank
    @Size(min=3, max = 60)
    @ApiModelProperty(notes = "User Name", required = true)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    @ApiModelProperty(notes = "Password", required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}