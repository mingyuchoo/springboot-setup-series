package com.mingyuchoo.swaggerdemo.controller;

import com.mingyuchoo.swaggerdemo.entity.Employee;
import com.mingyuchoo.swaggerdemo.repository.EmployeeRepository;
import io.swagger.annotations.*;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "임직원 API", description = "임직원에 대한 CRUD 수행")
public class EmployeeController {
    @Autowired EmployeeRepository employeeRepository;

    /**
     * getEmployees
     *
     * @return
     */
    @ApiOperation(value = "임직원 목록 조회", notes = "저장된 임직원에 대한 목록을 조회한다.", response = List.class)
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "성공적으로 목록을 조회함"),
                @ApiResponse(code = 404, message = "자원을 찾을 수 없음")
            })
    @GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getEmployees() {
        final List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    /**
     * getOneEmployee
     *
     * @param id
     * @return
     */
    @ApiOperation(
            value = "특정 임직원 상세 조회",
            notes = "요청한 ID에 대한 임직원 상세 정보를 조회한다.",
            response = Employee.class)
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "성공적으로 임직원 상세 정보를 조회함"),
                @ApiResponse(code = 404, message = "자원을 찾을 수 없음")
            })
    @GetMapping(value = "/employees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getOneEmployee(
            @ApiParam("Employee Id") @PathVariable("id") Long id) {
        final Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * postOneEmployee
     *
     * @param employee
     * @return
     */
    @ApiOperation(value = "신규 임직원 등록", notes = "신규 임직원 정보를 등록한다.", response = Employee.class)
    @ApiResponses(
            value = {
                @ApiResponse(code = 201, message = "새로운 임직원 정보를 성공적으로 등록함"),
                @ApiResponse(code = 417, message = "예상 실패")
            })
    @PostMapping(
            value = "/employees",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> postOneEmployee(@RequestBody Employee employee) {
        try {
            final Employee returnedEmployee = employeeRepository.save(employee);
            return new ResponseEntity<>(returnedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * putOneEmployee
     *
     * @param id
     * @param employee
     * @return
     */
    @ApiOperation(
            value = "특정 임직원 정보 수정",
            notes = "기존에 저장되어 있는 특정 임직원에 대해 특정 정보를 수정한다.",
            response = Employee.class)
    @ApiResponses(
            value = {
                @ApiResponse(code = 201, message = "임직원 정보를 성공적으로 수정함"),
                @ApiResponse(code = 404, message = "자원을 찾을 수 없음")
            })
    @PutMapping(
            value = "/employees/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> putOneEmployee(
            @ApiParam("Employee Id") @PathVariable("id") Long id, @RequestBody Employee employee) {
        final Optional<Employee> foundEmployee = employeeRepository.findById(id);
        if (foundEmployee.isPresent()) {
            Employee _employee = foundEmployee.get();
            _employee.setFirstName(employee.getFirstName());
            _employee.setLastName(employee.getLastName());
            _employee.setEmailId(employee.getEmailId());
            return new ResponseEntity<>(employeeRepository.save(_employee), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * deleteOneEmployee
     *
     * @param id
     * @return
     */
    @ApiOperation(
            value = "특정 임직원 정보 삭제",
            notes = "기존 저장되어 있는 특정 id에 대한 임직원 정보를 삭제한다.",
            response = HttpStatus.class)
    @ApiResponses(
            value = {
                @ApiResponse(code = 204, message = "임직원 정보를 성공적으로 삭제됨"),
                @ApiResponse(code = 417, message = "예상 실패")
            })
    @DeleteMapping(value = "/employees/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> deleteOneEmployee(
            @ApiParam("Employee Id") @PathVariable("id") Long id) {
        try {
            employeeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
