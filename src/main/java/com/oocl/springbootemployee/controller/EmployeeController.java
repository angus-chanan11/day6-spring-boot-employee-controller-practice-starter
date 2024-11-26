package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.model.AgeSalaryParam;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "")
    public List<Employee> getAll() {
        return employeeRepository.getAll();
    }

    @GetMapping(path = "/{id}")
    public Employee getById(@PathVariable Integer id) {
        return employeeRepository.getById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getAllByGender(@RequestParam Gender gender){
        return employeeRepository.getAllByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping(path = "/{id}")
    public Employee updateAgeSalaryById(@PathVariable Integer id, @RequestBody AgeSalaryParam ageSalaryParam) {
        return employeeRepository.updateEmployeeAgeSalary(id, ageSalaryParam);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Integer id) {
        employeeRepository.delete(id);
    }

    @GetMapping(params = {"page", "size"})
    public List<Employee> getEmployeesByPage(@RequestParam Integer page, @RequestParam Integer size) {
        return employeeRepository.getByPage(page, size);
    }
}
