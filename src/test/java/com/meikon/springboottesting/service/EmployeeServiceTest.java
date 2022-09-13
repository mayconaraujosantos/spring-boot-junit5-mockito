package com.meikon.springboottesting.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.exception.ResourceNotFoundException;
import com.meikon.springboottesting.domain.repository.EmployeeRepository;
import com.meikon.springboottesting.domain.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  private Employee employee;

  @BeforeEach
  void setup() {
    employee = Employee.builder().id(1L).firstName("firstName").lastName("lastName").email("m@gmail.com").build();
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

  @DisplayName("JUnit test for saveEmployee method witch throws exception")
  @Test
  void givenExistingEmail_whenSaveEmployee_thenThrowsExceptions() {
    // given
    given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
    // when
    ResourceNotFoundException thrown = catchThrowableOfType(() -> employeeService.saveEmployee(employee), ResourceNotFoundException.class);
    // then
    assertThat(thrown).hasMessage("Employee already exist with given email: " + employee.getEmail()).hasNoCause();
  }

  @DisplayName("JUnit test for get all employees method")
  @Test
  void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeesList() {
    // given
    var employeeBuild = Employee.builder().id(2L).firstName("firstName").lastName("lastName").email("email@gmail.com").build();
    given(employeeRepository.findAll()).willReturn(List.of(employee, employeeBuild));
    // when
    List<Employee> employeeList = employeeService.getAllEmployee();
    // then
    assertThat(employeeList).isNotNull();
    assertThat(employeeList.size()).isEqualTo(2);
  }

  @DisplayName("JUnit test for get employees method")
  @Test
  void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
    // given
    given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
    // when 
    Employee foundEmployee = employeeService.getEmployeeById(employee.getId()).get();

    // then
    assertThat(foundEmployee).isNotNull();
  }
}
