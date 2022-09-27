package com.meikon.springboottesting.unitary.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.service.EmployeeService;

@WebMvcTest
class EmployeeControllerTest {

  private static final String EMPLOYEE_URL = "/api/employees";
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void givenEmployeeObject_whenCreateEmployee_thenReturnsSaveEmployee() throws Exception {
    // given - precondition or setup
    Employee employee = Employee.builder()
        .firstName("Ramesh")
        .lastName("Fadatare")
        .email("ramesh@gmail.com")
        .build();
    given(employeeService.saveEmployee(any(Employee.class)))
        .willAnswer((invocation) -> invocation.getArgument(0));

    // when - action or behaviour that we are going test
    ResultActions response = mockMvc.perform(post(EMPLOYEE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(employee)));

    // then - verify the result or output using assert statements
    response.andDo(print()).andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstName",
            is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName",
            is(employee.getLastName())))
        .andExpect(jsonPath("$.email",
            is(employee.getEmail())));
  }

  @Test
  void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
    // given - precondition or setup
    List<Employee> listOfEmployee = new ArrayList<>();
    listOfEmployee.add(
        Employee.builder().firstName("firstName").lastName("lastName").email("m@gmail.com").build());
    listOfEmployee.add(
        Employee.builder().firstName("firstName").lastName("lastName").email("a@gmail.com").build());
    given(employeeService.getAllEmployee()).willReturn(listOfEmployee);
    // when - action or behaviour that we are going test
    ResultActions response = mockMvc.perform(get(EMPLOYEE_URL));
    // then - verify the result or output using assert statements
    response.andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(listOfEmployee.size())));
  }

  @Test
  void givenEmployeeId_whenGetByIdEmployees_thenReturnEmployeeList() throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    var employee = Employee.builder()
        .firstName("firstName")
        .lastName("lastName")
        .email("m@gmail.com")
        .build();
    given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));
    // when - action or behaviour that we are going test
    ResultActions response = mockMvc.perform(get(EMPLOYEE_URL + "/{id}", employeeId));
    // then - verify the result or output using assert statements
    response.andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
        .andExpect(jsonPath("$.email", is(employee.getEmail())));
  }

  @Test
  void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
    // given - precondition or setup
    long employeeId = 1L;
    var employee = Employee.builder()
        .firstName("firstName")
        .lastName("lastName")
        .email("m@gmail.com")
        .build();
    given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
    // when - action or behaviour that we are going test
    ResultActions response = mockMvc.perform(get(EMPLOYEE_URL + "/{id}", employeeId));
    // then - verify the result or output using assert statements
    response.andExpect(status().isNotFound())
        .andDo(print());
  }

}
