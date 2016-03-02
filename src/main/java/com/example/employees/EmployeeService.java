/* Copyright � 2015 Oracle and/or its affiliates. All rights reserved. */
package com.example.employees;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import java.util.List;

public class EmployeeService {

    List<Employee> employeeList = EmployeeList.getInstance();

    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    public Employee getEmployee(final int id) throws Exception {

        Optional<Employee> employeeOptional = FluentIterable.from(employeeList).firstMatch(new Predicate<Employee>() {
            public boolean apply(Employee input) {
                return id == input.getId();
            }
        });
        if (employeeOptional.isPresent()) {
            return employeeOptional.get();
        } else {
            throw new Exception("The Employee id " + id + " not found");
        }
    }

    public List<Employee> searchEmployeesByName(final String name) {
        ImmutableList<Employee> employees = FluentIterable.from(employeeList).filter(new Predicate<Employee>() {
            public boolean apply(Employee input) {
                return input.getName().equalsIgnoreCase(name) || input.getLastName().equalsIgnoreCase(name);
            }
        }).toImmutableList();
        return employees;
    }

    public long addEmployee(Employee employee) {
        employeeList.add(employee);
        return employee.getId();
    }

    public boolean updateEmployee(final Employee customer) {
        int matchIdx = 0;
        Optional<Employee> employeeOptional = FluentIterable.from(employeeList).firstMatch(new Predicate<Employee>() {
            public boolean apply(Employee input) {
                return customer.getId() == input.getId();
            }
        });
        if (employeeOptional.isPresent()) {
            matchIdx = employeeList.indexOf(employeeOptional.get());
            employeeList.set(matchIdx, customer);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteEmployee(int id) throws Exception {
        Employee employee = getEmployee(id);
        int matchIdx = employeeList.indexOf(employee);
        employeeList.remove(matchIdx);
        return true;
    }
}
