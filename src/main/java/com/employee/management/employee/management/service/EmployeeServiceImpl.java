package com.employee.management.employee.management.service;

import com.employee.management.employee.management.dto.EmployeeDTO;
import com.employee.management.employee.management.entity.Employee;
import com.employee.management.employee.management.exception.ResourceNotFoundException;
import com.employee.management.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
      this.employeeRepository=employeeRepository;
    }


    private EmployeeDTO convertToDTO(Employee e){
        EmployeeDTO dto= new EmployeeDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setEmail(e.getEmail());
        dto.setSalary(e.getSalary());
        return dto;
    }

    private Employee convertToEntity(EmployeeDTO dto){
        Employee emp= new Employee();
        emp.setId(dto.getId());
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setSalary(dto.getSalary());

        return emp;
    }



    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Employee savedEmployee=employeeRepository.save(convertToEntity(dto));

        return convertToDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
       Employee emp= employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee not found with id: "+ id));
        return convertToDTO(emp);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees(){
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee emp= employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(
                "Employee Not Found with id: "+id
        ));
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setSalary(dto.getSalary());
        return convertToDTO(employeeRepository.save(emp));
    }

    @Override
    public void deleteEmployee(Long id) {

        if(!employeeRepository.existsById(id)){
            throw new ResourceNotFoundException("Employee Not Found With Id: "+id);
        }
        employeeRepository.deleteById(id);
    }
}
