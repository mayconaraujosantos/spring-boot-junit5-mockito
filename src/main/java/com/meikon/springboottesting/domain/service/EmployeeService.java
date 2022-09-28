package com.meikon.springboottesting.domain.service;

import com.meikon.springboottesting.domain.entity.Employee;
import java.util.List;
import java.util.Optional;


public interface EmployeeService {
  Employee saveEmployee(Employee employee);

  List<Employee> getAllEmployee();

  Optional<Employee> getEmployeeById(long id);

  Employee updateEmployee(Employee employeeUpdated);

  void deleteEmployee(long id);
}
