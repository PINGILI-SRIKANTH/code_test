package com.employee.management.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.employee.management.entity.*;
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
