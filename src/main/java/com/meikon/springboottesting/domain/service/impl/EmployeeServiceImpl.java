package com.meikon.springboottesting.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.exception.ResourceNotFoundException;
import com.meikon.springboottesting.domain.repository.EmployeeRepository;
import com.meikon.springboottesting.domain.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public Employee saveEmployee(Employee employee) {
    Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
    if (savedEmployee.isPresent()) {
      throw new ResourceNotFoundException(
          "Employee already exist with given email: " + employee.getEmail());
    }
    return employeeRepository.save(employee);
  }

  @Override
  public List<Employee> getAllEmployee() {
    return employeeRepository.findAll();
  }

  @Override
  public Optional<Employee> getEmployeeById(long id) {
    return employeeRepository.findById(id);
  }

  @Override
  public Employee updatedEmployee(Employee employeeUpdated) {
    return employeeRepository.save(employeeUpdated);
  }


}
