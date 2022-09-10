package com.meikon.springboottesting.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.repository.EmployeeRepository;

@DataJpaTest
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  // JUnit test for save employee operation
  @DisplayName("JUnit test for save employee operation")
  @Test
  public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
    // given - precondition or setup
    Employee employee = Employee.builder().firstName("firstName").lastName("lastName")
        .email("email@gmail.com").build();
    // when - action or the behaviour that we are going test
    Employee savedEmployee = employeeRepository.save(employee);
    // then - verify the output
    Assertions.assertThat(savedEmployee).isNotNull();
    Assertions.assertThat(savedEmployee.getId()).isPositive();
  }
}
