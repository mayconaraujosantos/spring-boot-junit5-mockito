package com.meikon.springboottesting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.meikon.springboottesting.domain.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {  
}
