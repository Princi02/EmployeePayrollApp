package com.bridzlab.employeepayrollapp.controller;

import com.bridzlab.employeepayrollapp.dto.EmployeeDTO;
import com.bridzlab.employeepayrollapp.model.Employee;
import com.bridzlab.employeepayrollapp.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // GET: Retrieve all employees
    @GetMapping("/")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // POST: Create a new employee
    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeDTO));
    }

    // GET: Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: Update employee details
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return (updatedEmployee != null) ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.notFound().build();
    }

    // DELETE: Delete employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        if (employeeService.getEmployeeById(id).isPresent()) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    check logging by lombok
    @GetMapping("/logtest")
    public String logExample() {
        log.info("This is an INFO log message!");
        log.debug("This is a DEBUG log message!");
        log.error("This is an ERROR log message!");
        return "Logging enabled!";
    }
}
