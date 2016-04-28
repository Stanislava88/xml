package com.clouway.saxparser.core;

import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Employees {
  List<Employee> employees;

  public Employees(List<Employee> employees) {
    this.employees = employees;
  }

  public Employees() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Employees employees1 = (Employees) o;

    return employees != null ? employees.equals(employees1.employees) : employees1.employees == null;

  }

  @Override
  public int hashCode() {
    return employees != null ? employees.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Employees{" +
            "employees=" + employees +
            '}';
  }
}
