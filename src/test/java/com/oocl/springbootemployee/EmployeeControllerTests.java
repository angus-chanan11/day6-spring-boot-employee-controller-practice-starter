package com.oocl.springbootemployee;

import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    private EmployeeRepository employeeRepository;
    @Autowired
    private JacksonTester<List<Employee>> employeesJsonTester;
    @Autowired
    private JacksonTester<Employee> employeeJacksonTester;

    @BeforeEach
    void setUp() {
        employeeRepository.getAll().clear();
        employeeRepository.save(new Employee(0, "Emily", 10, Gender.FEMALE, 10000.0));
        employeeRepository.save(new Employee(1, "Angus", 10, Gender.MALE, 10000.0));
    }

    @Test
    public void should_retrun_all_employees_when_get_all() throws Exception {
        List<Employee> expected_employees = employeeRepository.getAll();

        String employeeListJson = client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(employeesJsonTester.parse(employeeListJson)).usingRecursiveComparison().isEqualTo(expected_employees);
    }

    @Test
    public void should_retrun_employee_id1_when_getById_given_id1() throws Exception {
        Employee expected_employee = employeeRepository.getById(1);

        String employeeJson = client.perform(MockMvcRequestBuilders.get("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(employeeJacksonTester.parse(employeeJson)).usingRecursiveComparison().isEqualTo(expected_employee);
    }

    @Test
    public void should_retrun_male_employee_when_getByGender_given_male() throws Exception {
        List<Employee> expected_employee = employeeRepository.getAllByGender(Gender.MALE);

        String employeeJson = (client.perform((MockMvcRequestBuilders.get("/employees"))
                .param("gender", "MALE")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(employeesJsonTester.parse(employeeJson)).usingRecursiveComparison().isEqualTo(expected_employee);
    }

    @Test
    public void should_create_employee_success() throws Exception {
        String employee = """
                {
                    "name": "Jacky",
                    "age": 12,
                    "gender": "MALE",
                    "salary": 10000.0
                }
                """;

        final Employee expected_employee = new Employee(2, "Jacky", 12, Gender.MALE, 10000.0);
        String employeeJson = client.perform((MockMvcRequestBuilders.post("/employees"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();
        assertThat(employeeJacksonTester.parse(employeeJson)).usingRecursiveComparison().isEqualTo(expected_employee);
    }

    @Test
    public void should_update_age_and_salary_when_updateAgeAndSalary_given_id() throws Exception {
        String newAgeAndSalary = """
                {
                    "age": 18,
                    "salary": 12000.0
                }
                """;

        final Employee expected_employee = new Employee(0, "Emily", 18, Gender.FEMALE, 12000.0);
        String employeeJson = client.perform((MockMvcRequestBuilders.put("/employees/0"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newAgeAndSalary))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(employeeJacksonTester.parse(employeeJson)).usingRecursiveComparison().isEqualTo(expected_employee);
    }

    @Test
    public void should_delete_employee_success() throws Exception {
        client.perform(MockMvcRequestBuilders.delete("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertEquals(1, employeeRepository.getAll().size());
    }

    @Test
    public void should_return_employees_by_page() throws Exception {
        List<Employee> expected_employees = employeeRepository.getByPage(0, 1);
        String employeeListJson = client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page", "0")
                        .param("size", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(employeesJsonTester.parse(employeeListJson)).usingRecursiveComparison().isEqualTo(expected_employees);
    }
}


