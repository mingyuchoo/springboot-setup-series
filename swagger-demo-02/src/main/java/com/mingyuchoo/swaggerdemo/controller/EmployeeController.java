package com.mingyuchoo.swaggerdemo.controller;

import com.mingyuchoo.swaggerdemo.entity.Employee;
import com.mingyuchoo.swaggerdemo.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Employee API", description = "Operations pertaining to Employee")
public class EmployeeController {
    @Autowired EmployeeRepository employeeRepository;

    @ApiOperation(value = "View a list of employees")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "Successfully retrieved list"),
                @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
                @ApiResponse(
                        code = 403,
                        message = "Accessing the resource you were trying to reach is forbidden"),
                @ApiResponse(
                        code = 404,
                        message = "The resource you were trying to reach is not found")
            })
    @GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getEmployees() {
        final List<Employee> employees = employeeRepository.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @ApiOperation(value = "Post a employee")
    @ApiResponses(
            value = {
                @ApiResponse(code = 201, message = "Successfully created one employee"),
                @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
                @ApiResponse(
                        code = 403,
                        message = "Accessing the resource you were trying to reach is forbidden"),
                @ApiResponse(
                        code = 404,
                        message = "The resource you were trying to reach is not found")
            })
    @PostMapping(
            value = "/employees",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> postOneEmployee(@RequestBody Employee employee) {
        final Employee returnedEmployee = employeeRepository.save(employee);
        return new ResponseEntity<>(returnedEmployee, HttpStatus.CREATED);
    }
}
