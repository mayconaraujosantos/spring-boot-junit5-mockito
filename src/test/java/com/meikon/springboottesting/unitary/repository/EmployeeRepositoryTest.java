package com.meikon.springboottesting.unitary.repository;

import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.repository.EmployeeRepository;
import com.meikon.springboottesting.utils.EmployeeUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  private Employee employee;

  @BeforeEach
  void setup() {
    employee =
      Employee
        .builder()
        .firstName("firstName")
        .lastName("lastName")
        .email("marcos@gmail.com")
        .build();
  }

  // JUnit test for save employee operation
  @DisplayName("JUnit test for save employee operation")
  @Test
  void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
    Employee savedEmployee = employeeRepository.save(employee);

    Assertions.assertThat(savedEmployee).isNotNull();
    Assertions.assertThat(savedEmployee.getId()).isPositive();
  }

  @DisplayName("JUnit test for get all employee operation")
  @Test
  void giveEmployeeList_whenFindAll_then() {
    List<Employee> employee = EmployeeUtils.createListOfEmployee();
    employeeRepository.saveAll(employee);
    List<Employee> employeeList = employeeRepository.findAll();

    assertThat(employeeList).isNotNull();
    assertThat(employeeList.size()).isEqualTo(2);
  }

  @DisplayName("JUnit test for get employee by id operation")
  @Test
  void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
    employeeRepository.save(employee);
    Optional<Employee> employeeId = employeeRepository.findById(
      employee.getId()
    );

    assertThat(employeeId).isNotNull();
  }

  @DisplayName("JUnit test for get employee by email operation")
  @Test
  void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
    employeeRepository.save(employee);
    Optional<Employee> employeeEmail = employeeRepository.findByEmail(
      employee.getEmail()
    );

    assertThat(employeeEmail).isNotNull();
  }

  @DisplayName("JUnit test for update employee operation")
  @Test
  void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
    employeeRepository.save(employee);
    Employee savedEmployee = employeeRepository
      .findById(employee.getId())
      .orElse(null);
    savedEmployee.setFirstName("Fernanda");
    savedEmployee.setLastName("Souza");
    savedEmployee.setEmail("ad@gmail.com");
    Employee updatedEmployee = employeeRepository.save(savedEmployee);

    assertThat(updatedEmployee.getEmail())
      .isNotNull()
      .isEqualTo("ad@gmail.com");
    assertThat(updatedEmployee.getFirstName())
      .isNotNull()
      .isEqualTo("Fernanda");
    assertThat(updatedEmployee.getLastName()).isNotNull().isEqualTo("Souza");
  }

  @DisplayName("JUnit test for delete employee operation")
  @Test
  void givenEmployeeObject_whenDelete_thenReturnRemoveEmployee() {
    employeeRepository.save(employee);
    employeeRepository.delete(employee);
    Optional<Employee> employeeOptional = employeeRepository.findById(
      employee.getId()
    );

    assertThat(employeeOptional).isEmpty();
  }

  @DisplayName("JUnit test for custom query using JPQL employee operation")
  @Test
  void givenFirstNameNadLastName_whenFindByJPQL_thenReturnEmployeeObject() {
    employeeRepository.save(employee);

    String firstName = "firstName";
    String lastName = "lastName";

    Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

    assertThat(savedEmployee).isNotNull();
  }

  @DisplayName(
    "JUnit test for custom query using JPQL with Named params in employee operation"
  )
  @Test
  void givenFirstNameNadLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
    employeeRepository.save(employee);
    String firstName = "firstName";
    String lastName = "lastName";

    Employee savedEmployee = employeeRepository.findByJPQLNamedParams(
      firstName,
      lastName
    );

    assertThat(savedEmployee).isNotNull();
  }

  @DisplayName("JUnit test for custom query using native SQL with index")
  @Test
  void giveFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
    employeeRepository.save(employee);

    Employee savedEmployee = employeeRepository.findByNativeSQL(
      employee.getFirstName(),
      employee.getLastName()
    );

    assertThat(savedEmployee).isNotNull();
  }

  @DisplayName("JUnit test for custom query using native SQL with named params")
  @Test
  void giveFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
    employeeRepository.save(employee);

    Employee savedEmployee = employeeRepository.findByNativeSQLNamed(
      employee.getFirstName(),
      employee.getLastName()
    );

    assertThat(savedEmployee).isNotNull();
  }
}
