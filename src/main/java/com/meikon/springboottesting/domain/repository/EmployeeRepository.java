package com.meikon.springboottesting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.meikon.springboottesting.domain.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {  
  Optional<Employee> findByEmail(String email);
  
  @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
  Employee findbyJPQL(String firstName, String lastName);
}
