package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository(){
        employees.add(new Employee(1, "Emily", 10, Gender.FEMALE, 10000.0));
        employees.add(new Employee(2, "Angus", 10, Gender.MALE, 10000.0));
    }
    public List<Employee> getAll() {
        return employees;
    }

    public Employee getById(int id) {
        return employees.stream().filter(employee -> employee.getId() ==id).findFirst().orElse(null);
    }

    public List<Employee> getAllByGender(Gender gender) {
        return employees.stream().filter(employee -> employee.getGender() == gender).toList();
    }

    public Employee save(Employee employee) {
        employee.setId(employees.size()+1);
        employees.add(employee);
        return employee;
    }
}
