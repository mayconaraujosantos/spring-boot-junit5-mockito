package com.meikon.springboottesting.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.repository.EmployeeRepository;

@DataJpaTest
class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  // JUnit test for save employee operation
  @DisplayName("JUnit test for save employee operation")
  @Test
  void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
    // given - precondition or setup
    Employee employee = this.createPersistOfEmployee();
    // when - action or the behaviour that we are going test
    Employee savedEmployee = employeeRepository.save(employee);
    // then - verify the output
    Assertions.assertThat(savedEmployee).isNotNull();
    Assertions.assertThat(savedEmployee.getId()).isPositive();
  }

  @DisplayName("JUnit test for get all employee operation")
  @Test
  void giveEmployeeList_whenFindAll_then() {
     // given 
    List<Employee> employee = createListOfEmployee();
    employeeRepository.saveAll(employee);
    // when 
    List<Employee> employeeList = employeeRepository.findAll();
    // then 
    assertThat(employeeList).isNotNull();
    assertThat(employeeList.size()).isEqualTo(2);
  }
  
  @DisplayName("JUnit test for get employee by id operation")
  @Test
  void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
    // given
    Employee employee = createPersistOfEmployee();
    employeeRepository.save(employee);
    // when
    Optional<Employee> employeeId = employeeRepository.findById(employee.getId());
    // then
    assertThat(employeeId).isNotNull();
  }

  private Employee createPersistOfEmployee() {
    return Employee.builder().firstName("firstName").lastName("lastName").email("marcos@gmail.com").build();
  }
  private List<Employee> createListOfEmployee() {
    List<Employee> employeesList = new ArrayList<>();
    employeesList.add(Employee.builder().firstName("Marcos").lastName("Santos").email("marcos@gmail.com").build());
    employeesList.add(Employee.builder().firstName("Adriano").lastName("Lopes").email("adriano@mail.com").build());
    return employeesList;
  }

}
