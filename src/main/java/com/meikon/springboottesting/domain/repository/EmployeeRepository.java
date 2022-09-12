package com.meikon.springboottesting.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.meikon.springboottesting.domain.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByEmail(String email);

  @Query("SELECT e FROM Employee e WHERE e.firstName = ?1 and e.lastName = ?2")
  Employee findByJPQL(String firstName, String lastName);

  @Query("SELECT e FROM Employee e WHERE e.firstName =:firstName and e.lastName =:lastName")
  Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

  @Query(value = "SELECT * FROM employees e WHERE e.first_name =?1 and e.last_name =?2", nativeQuery = true)
  Employee findByNativeSQL(String firstName, String lastName);  
  
  @Query(value = "SELECT * FROM employees e WHERE e.first_name =:firstName and e.last_name =:lastName", nativeQuery = true)
  Employee findByNativeSQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
