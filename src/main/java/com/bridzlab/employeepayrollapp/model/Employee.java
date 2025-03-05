package com.bridzlab.employeepayrollapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data // Lombok annotation to generate Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates an all-args constructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Department is required")
    private String department;

    @Min(value = 10000, message = "Salary must be at least 10,000")
    private double salary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Department is required") String getDepartment() {
        return department;
    }

    public void setDepartment(@NotBlank(message = "Department is required") String department) {
        this.department = department;
    }

    public @Min(value = 10000, message = "Salary must be at least 10,000") double getSalary() {
        return salary;
    }

    public void setSalary(@Min(value = 10000, message = "Salary must be at least 10,000") double salary) {
        this.salary = salary;
    }
}
