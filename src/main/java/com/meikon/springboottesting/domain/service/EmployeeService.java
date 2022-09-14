package com.meikon.springboottesting.domain.service;

import java.util.List;
import java.util.Optional;
import com.meikon.springboottesting.domain.entity.Employee;

public interface EmployeeService {
  Employee saveEmployee(Employee employee);

  List<Employee> getAllEmployee();

  Optional<Employee> getEmployeeById(long id);

  Employee updatedEmployee(Employee employeeUpdated);
}
