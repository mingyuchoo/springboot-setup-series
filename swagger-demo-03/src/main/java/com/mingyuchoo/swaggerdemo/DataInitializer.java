package com.mingyuchoo.swaggerdemo;

import com.mingyuchoo.swaggerdemo.entity.Employee;
import com.mingyuchoo.swaggerdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired EmployeeRepository employeeRepository;

    public void initData() {
        try {
            Employee emp = new Employee("Will", "Smith", "wsmith@email.com");
            employeeRepository.save(emp);

        } catch (final Exception ex) {
            throw ex;
        }
    }
}
