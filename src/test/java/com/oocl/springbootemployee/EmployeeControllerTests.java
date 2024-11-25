package com.oocl.springbootemployee;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc

@AutoConfigureJsonTesters
public class EmployeeControllerTests {

    @Autowired
    private MockMvc client;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private JacksonTester<List<Employee>> listJson;
    @Autowired
    private JacksonTester<Employee> json;

    @Test
    public void should_retrun_all_employees_when_get_all() throws Exception {

        final List<Employee> expected_employees = employeeRepository.getAll();

        String employeeListJson = client.perform(MockMvcRequestBuilders.get("/employees")).andReturn().getResponse().getContentAsString();
        assertThat(listJson.parse(employeeListJson)).usingRecursiveComparison().isEqualTo(expected_employees);
    }

    @Test
    public void should_retrun_employee_id1_when_getById_given_id1() throws Exception {

        final Employee expected_employee = employeeRepository.getById(1);

        String employeeJson = client.perform(MockMvcRequestBuilders.get("/employees/1")).andReturn().getResponse().getContentAsString();
        assertThat(json.parse(employeeJson)).usingRecursiveComparison().isEqualTo(expected_employee);
    }
    @Test
    public void should_retrun_male_employee_when_getByGender_given_male() throws Exception {

        final List<Employee> expected_employee = employeeRepository.getAllByGender(Gender.MALE);

        String employeeJson = (client.perform((MockMvcRequestBuilders.get("/employees")).param("gender","MALE")))
                .andReturn().getResponse().getContentAsString();
        assertThat(listJson.parse(employeeJson)).usingRecursiveComparison().isEqualTo(expected_employee);
    }

    @Test
    public void should_create_employee_success() throws Exception {
        // Given
        String employee = """
                {
                    "name": "Jacky",
                    "age": 12,
                    "gender": "MALE",
                    "salary": 10000.0
                }""";

        Employee expected_employee = new Employee(3, "Jacky", 12, Gender.MALE, 10000.0);
        // When
        String employeeJson = client.perform((MockMvcRequestBuilders.post("/employees"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                        .andReturn().getResponse().getContentAsString();
        assertThat(json.parse(employeeJson)).usingRecursiveComparison().isEqualTo(expected_employee);
        // Then


    }
}
