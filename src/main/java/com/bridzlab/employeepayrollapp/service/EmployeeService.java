package com.bridzlab.employeepayrollapp.service;

import com.bridzlab.employeepayrollapp.dto.EmployeeDTO;
import com.bridzlab.employeepayrollapp.exception.EmployeeNotFoundException;
import com.bridzlab.employeepayrollapp.model.Employee;
import com.bridzlab.employeepayrollapp.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private List<Employee> employeeList = new ArrayList<>(); // In-memory list

    // Load data from DB to List when the app starts
    @PostConstruct
    public void loadEmployeesFromDatabase() {
        employeeList = employeeRepository.findAll();
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employeeList);
    }

    // Get employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeList.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst()
                .or(() -> {
                    throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
                });
    }

    // Create employee
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment("null"); // Default department

        Employee savedEmployee = employeeRepository.save(employee); // Save to DB
        employeeList.add(savedEmployee); // Add to list
        return savedEmployee;
    }

    // Update employee
    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setName(employeeDTO.getName());
            employee.setSalary(employeeDTO.getSalary());
            employee.setDepartment("null");

            Employee updatedEmployee = employeeRepository.save(employee);

            employeeList.removeIf(emp -> emp.getId().equals(id));
            employeeList.add(updatedEmployee);
            return updatedEmployee;
        }
        throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
        }
        employeeRepository.deleteById(id);
        employeeList.removeIf(emp -> emp.getId().equals(id));
    }
}
