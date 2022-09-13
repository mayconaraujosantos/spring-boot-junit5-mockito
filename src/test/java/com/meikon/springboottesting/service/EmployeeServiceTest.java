package com.meikon.springboottesting.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.repository.EmployeeRepository;
import com.meikon.springboottesting.domain.service.EmployeeService;
import com.meikon.springboottesting.domain.service.impl.EmployeeServiceImpl;

public class EmployeeServiceTest {

  private EmployeeRepository employeeRepository;

  private EmployeeService employeeService;

  @BeforeEach
  void setup() {
    employeeRepository = Mockito.mock(EmployeeRepository.class);
    employeeService = new EmployeeServiceImpl(employeeRepository);
  }

  @DisplayName("JUnit for save employee method")
  @Test
  void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
    // given
    Employee employee = Employee.builder().id(1L).firstName("firstName").lastName("lastName")
        .email("m@gmail.com").build();
    BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
        .willReturn(Optional.empty());
    BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
    // when
    Employee savedEmployee = employeeService.saveEmployee(employee);
    // then
    assertThat(savedEmployee).isNotNull();
  }
}
