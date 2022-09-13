package com.meikon.springboottesting.domain.service;

import com.meikon.springboottesting.domain.entity.Employee;

import java.util.List;

public interface EmployeeService {
  Employee saveEmployee(Employee employee);
  List<Employee> getAllEmployee();
}
