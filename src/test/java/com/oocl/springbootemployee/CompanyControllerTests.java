package com.oocl.springbootemployee;

import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.model.Gender;
import com.oocl.springbootemployee.repository.CompanyRepository;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc

@AutoConfigureJsonTesters
public class CompanyControllerTests {

    @Autowired
    private MockMvc client;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JacksonTester<List<Company>> listJson;

    @BeforeEach
    void setUp() {
        companyRepository.getAll().clear();
        companyRepository.save(new Company(0, "OOCL"));
        companyRepository.save(new Company(1, "COSCO"));
    }

    @Test
    public void should_retrun_all_companies_when_get_all() throws Exception {
        List<Company> expected_companies = companyRepository.getAll();

        String companyListJson = client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(listJson.parse(companyListJson)).usingRecursiveComparison().isEqualTo(expected_companies);
    }
}
