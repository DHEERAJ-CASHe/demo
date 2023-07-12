package com.employee.demo.controller;

import com.employee.demo.exception.ResourceNotFoundException;
import com.employee.demo.model.Employee;
import com.employee.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/")

public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    // get all employees
    @GetMapping(path = "/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    //create employee
    @PostMapping(path = "/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
    @GetMapping(path = "/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee= employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+ id));
        return ResponseEntity.ok(employee);
    }
    @PutMapping(path = "/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
            Employee employee= employeeRepository.findById(id).
                    orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+ id));
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setEmailId(employeeDetails.getEmailId());
           Employee updatedEmployee = employeeRepository.save(employee);
           return ResponseEntity.ok(updatedEmployee);
    }
    @DeleteMapping(path = "/employees/{id}")
    public  ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->new ResourceNotFoundException("Employee not exist with id :"+ id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response= new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
