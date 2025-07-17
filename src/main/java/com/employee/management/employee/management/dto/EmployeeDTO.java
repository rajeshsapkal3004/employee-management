package com.employee.management.employee.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name is Required")
    private String name;

    @Email(message = "Email should be Valid")
    @NotBlank(message = "Email is Required")
    private String email;

    
    private double salary;

}
