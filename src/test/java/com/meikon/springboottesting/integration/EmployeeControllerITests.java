package com.meikon.springboottesting.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.repository.EmployeeRepository;
import com.meikon.springboottesting.utils.EmployeeUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITests {

  private static final String EMPLOYEE_URL = "/api/employees";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    employeeRepository.deleteAll();
  }

  @Test
  void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee()
    throws Exception {
    // given - precondition or setup
    var employee = EmployeeUtils.buildEmployee();
    // when - action or behaviour that we are going test
    ResultActions response = mockMvc.perform(
      post(EMPLOYEE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(employee))
    );
    // then - verify the result or output using assert statements
    response
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
      .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
      .andExpect(jsonPath("$.email", is(employee.getEmail())));
  }

  // JUnit test for Get All employees REST API
  @Test
  void givenListOfEmployees_whenGetAllEmployees_thenReturnsEmployeesList()
    throws Exception {
    // given - precondition or setup
    List<Employee> listOfEmployees = EmployeeUtils.createListOfEmployee();
    employeeRepository.saveAll(listOfEmployees);
    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(get(EMPLOYEE_URL));
    // then - verify the output
    response
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
  }

  // positive scenario - valid employee id
  // JUnit test for GET employee by id REST API
  @Test
  void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject()
    throws Exception {
    var employee = EmployeeUtils.buildEmployee();
    employeeRepository.save(employee);
    ResultActions response = mockMvc.perform(
      get(EMPLOYEE_URL + "/{id}", employee.getId())
    );

    response
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
      .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
      .andExpect(jsonPath("$.email", is(employee.getEmail())));
  }

  // negative scenario - valid employee id
  // JUnit test for GET employee by id REST API
  @Test
  void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty()
    throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    var employee = EmployeeUtils.buildEmployee();
    employeeRepository.save(employee);
    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(
      get(EMPLOYEE_URL + "/{id}", employeeId)
    );
    // then - verify the output
    response.andExpect(status().isNotFound()).andDo(print());
  }

  @Test
  void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject()
    throws Exception {
    // given - precondition or setup
    Employee savedEmployee = Employee
      .builder()
      .firstName("Ramesh")
      .lastName("Fadatare")
      .email("ramesh@gmail.com")
      .build();
    employeeRepository.save(savedEmployee);

    Employee updatedEmployee = Employee
      .builder()
      .firstName("Ram")
      .lastName("Jadhav")
      .email("ram@gmail.com")
      .build();
    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(
      put(EMPLOYEE_URL + "/{id}", savedEmployee.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedEmployee))
    );
    // then - verify the output
    response
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
      .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
      .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
  }

  // JUnit test for update employee REST API - negative scenario
  @Test
  void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404()
    throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    Employee savedEmployee = Employee
      .builder()
      .firstName("Ramesh")
      .lastName("Fadatare")
      .email("ramesh@gmail.com")
      .build();
    employeeRepository.save(savedEmployee);

    Employee updatedEmployee = Employee
      .builder()
      .firstName("Ram")
      .lastName("Jadhav")
      .email("ram@gmail.com")
      .build();
    // when -  action or the behaviour that we are going test
    ResultActions response = mockMvc.perform(
      put(EMPLOYEE_URL + "/{id}", employeeId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedEmployee))
    );

    // then - verify the output
    response.andExpect(status().isNotFound()).andDo(print());
  }

  @Test
  void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
    var employee = EmployeeUtils.buildEmployee();
    employeeRepository.save(employee);

    ResultActions response = mockMvc.perform(
      delete(EMPLOYEE_URL + "/{id}", employee.getId())
    );
    response.andExpect(status().isOk()).andDo(print());
  }
}
