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
  
  @DisplayName("JUnit test for get employee by email operation")
  @Test
  void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
    // given
      Employee employee = createPersistOfEmployee(); 
      employeeRepository.save(employee);
    // when
    Optional<Employee> employeeEmail = employeeRepository.findByEmail(employee.getEmail());
    // then
    assertThat(employeeEmail).isNotNull();
  }
  
  @DisplayName("JUnit test for update employee operation")
  @Test
  void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
    // given
    Employee employee = createPersistOfEmployee();
    employeeRepository.save(employee);
    // when
    Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
    savedEmployee.setFirstName("Fernanda");
    savedEmployee.setLastName("Souza");
    savedEmployee.setEmail("ad@gmail.com");
    Employee updatedEmployee = employeeRepository.save(savedEmployee);
    // then
    assertThat(updatedEmployee.getEmail()).isNotNull().isEqualTo("ad@gmail.com");
    assertThat(updatedEmployee.getFirstName()).isNotNull().isEqualTo("Fernanda");
    assertThat(updatedEmployee.getLastName()).isNotNull().isEqualTo("Souza");
  }
  @DisplayName("JUnit test for delete employee operation")
  @Test
  void givenEmployeeObject_whenDelete_thenReturnRemoveEmployee() {
    // given
    Employee employee = createPersistOfEmployee();
    employeeRepository.save(employee);
    // when
    employeeRepository.delete(employee);
    Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
    // then
    assertThat(employeeOptional).isEmpty();
  }
  @DisplayName("JUnit test for custom query using JPQL employee operation")
  @Test
  void givenFirstNameNadLastName_whenFindByJPQL_thenReturnEmployeeObject() {
    // given
    Employee employee = createPersistOfEmployee();
    employeeRepository.save(employee);
    String firstName = "firstName";
    String lastName = "lastName";
    // when
    Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);
    // then
    assertThat(savedEmployee).isNotNull();
  }
  @DisplayName("JUnit test for custom query using JPQL with Named params in employee operation")
  @Test
  void givenFirstNameNadLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
    // given
    Employee employee = createPersistOfEmployee();
    employeeRepository.save(employee);
    String firstName = "firstName";
    String lastName = "lastName";
    // when
    Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);
    // then
    assertThat(savedEmployee).isNotNull();
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
