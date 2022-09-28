package com.meikon.springboottesting.web.v1;

import com.meikon.springboottesting.domain.entity.Employee;
import com.meikon.springboottesting.domain.service.EmployeeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Employee createEmployee(@RequestBody Employee employee) {
    return employeeService.saveEmployee(employee);
  }

  @GetMapping
  public List<Employee> getAllEmployee() {
    return employeeService.getAllEmployee();
  }

  @GetMapping("{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId) {
    return employeeService.getEmployeeById(employeeId)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId,
    @RequestBody Employee employee) {
    return employeeService.getEmployeeById(employeeId)
      .map(savedEmployee -> {

        savedEmployee.setFirstName(employee.getFirstName());
        savedEmployee.setLastName(employee.getLastName());
        savedEmployee.setEmail(employee.getEmail());

        Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);

      })
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
  public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId) {
    employeeService.deleteEmployee(employeeId);
    return new ResponseEntity<>("Employee deleted successfully!.", HttpStatus.OK);
  }
}
