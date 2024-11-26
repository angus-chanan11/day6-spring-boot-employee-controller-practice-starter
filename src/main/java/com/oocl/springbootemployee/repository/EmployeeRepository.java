package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.AgeSalaryParam;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        employees.add(new Employee(0, "Emily", 10, Gender.FEMALE, 10000.0));
        employees.add(new Employee(1, "Angus", 10, Gender.MALE, 10000.0));
    }

    public List<Employee> getAll() {
        return employees;
    }

    public Employee getById(Integer id) {
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Employee> getAllByGender(Gender gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).toList();
    }

    public Employee save(Employee employee) {
        employee.setId(employees.size());
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployeeAgeSalary(Integer id, AgeSalaryParam ageSalaryParam) {
        Integer age = ageSalaryParam.getAge();
        Double salary = ageSalaryParam.getSalary();
        Employee targetEmployee = getById(id);
        if (Objects.nonNull(targetEmployee)) {
            targetEmployee.setAge(age);
            targetEmployee.setSalary(salary);
        }
        return targetEmployee;
    }

    public void delete(Integer id) {
        employees.removeIf(employee -> employee.getId().equals(id));
    }

    public List<Employee> getByPage(Integer page, Integer size) {
        return employees.subList(page * size, page * size + size);
    }
}
