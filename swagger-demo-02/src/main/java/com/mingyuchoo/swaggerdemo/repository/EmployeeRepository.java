package com.mingyuchoo.swaggerdemo.repository;

import com.mingyuchoo.swaggerdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
