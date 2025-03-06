package com.bridzlab.employeepayrollapp.service;

import com.bridzlab.employeepayrollapp.dto.EmployeeDTO;
import com.bridzlab.employeepayrollapp.model.Employee;
import com.bridzlab.employeepayrollapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment("null"); // Default value
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setName(employeeDTO.getName());
            employee.setSalary(employeeDTO.getSalary());
            employee.setDepartment("null");
            return employeeRepository.save(employee);
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        }
    }
}
