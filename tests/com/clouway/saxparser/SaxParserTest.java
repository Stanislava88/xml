package com.clouway.saxparser;

import com.clouway.saxparser.core.Address;
import com.clouway.saxparser.core.Addresses;
import com.clouway.saxparser.core.Employee;
import com.clouway.saxparser.core.Employees;
import com.clouway.saxparser.core.Employer;
import com.clouway.saxparser.core.Parser;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class SaxParserTest {
  private Parser saxParser = new SaxParser();

  @Test
  public void happyPath() throws Exception {
    InputStream inputStream = getClass().getResourceAsStream("employees.xml");

    Employer employer = new Employer("ClouWay", "22-10-2010", "10-10-2015");
    Address address = new Address("Veliki Preslav", "22", "B", "Sofia");
    Addresses addresses = new Addresses(Lists.newArrayList(address, address));
    Employee employee = new Employee("Lilia", "Angelova", "23", "accountant", employer, addresses);

    Employees employees = new Employees(Lists.newArrayList(employee, employee));

    List<Employees> actual = saxParser.parseList(inputStream, Employees.class);
    List<Employees> expected = Lists.newArrayList(employees);

    assertThat(actual, is(expected));
  }

  @Test
  public void parseUnknownFile() throws Exception {
    List<Employee> actual = saxParser.parseList(null, Employee.class);
    List<Employee> expected = Lists.newArrayList();

    assertThat(actual, is(expected));
  }
}
