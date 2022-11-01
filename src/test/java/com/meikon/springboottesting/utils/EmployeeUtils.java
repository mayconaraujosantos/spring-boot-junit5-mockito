package com.meikon.springboottesting.utils;

import com.meikon.springboottesting.domain.entity.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeUtils {

  public static List<Employee> createListOfEmployee() {
    List<Employee> employeesList = new ArrayList<>();
    employeesList.add(
      Employee
        .builder()
        .firstName("Marcos")
        .lastName("Santos")
        .email("marcos@gmail.com")
        .build()
    );
    employeesList.add(
      Employee
        .builder()
        .firstName("Adriano")
        .lastName("Lopes")
        .email("adriano@mail.com")
        .build()
    );
    return employeesList;
  }

  public static Employee buildEmployee() {
    var employee = Employee
      .builder()
      .firstName("Marcos")
      .lastName("Santos")
      .email("msantos@gmail.com")
      .build();
    return employee;
  }
}
