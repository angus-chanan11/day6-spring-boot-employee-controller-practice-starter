package com.oocl.springbootemployee.model;

public class AgeSalaryParam {
    private Integer age;
    private Double salary;

    public AgeSalaryParam(Integer age, Double salary) {
        this.age = age;
        this.salary = salary;
    }

    public Double getSalary() {
        return salary;
    }

    public Integer getAge() {
        return age;
    }
}
