package com.meikon.springboottesting.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.repository.EmployeeRepository;
import com.meikon.springboottesting.domain.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  private Employee employee;

  @BeforeEach
  void setup() {
    employee = Employee.builder().id(1L).firstName("firstName").lastName("lastName")
        .email("m@gmail.com").build();
  }

  @DisplayName("JUnit for save employee method")
  @Test
  void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
    // given
    given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
    given(employeeRepository.save(employee)).willReturn(employee);
    // when
    Employee savedEmployee = employeeService.saveEmployee(employee);
    // then
    assertThat(savedEmployee).isNotNull();
  }
}
