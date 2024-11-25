package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository = new EmployeeRepository();

    @GetMapping(path = "")
    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }
    @GetMapping(path = "/{id}")
    public Employee getById(@PathVariable int id) {
        return employeeRepository.getById(id);
    }
}
