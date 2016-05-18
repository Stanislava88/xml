package com.clouway.domparser;

import com.clouway.domparser.core.*;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class DomParserTest {
  private DomParser domParser = new DomParser();

  @Test
  public void happyPath() throws Exception {
    InputStream inputStream = this.getClass().getResourceAsStream("employees.xml");

    Employer employer = new Employer("Penny", ("12-12-2010"), ("25-12-2030"));
    Address address = new Address("Veliki Preslav", "22", "B", "Sofia");
    AddressBook addresses = new AddressBook(Lists.newArrayList(address, address, address));
    Employee employee = new Employee("Lilia", "Angelova", "23", "accountant", employer, addresses);

    Employees employees = new Employees(Lists.newArrayList(employee, employee));

    List<Employees> actual = domParser.parseList(inputStream, Employees.class);
    List<Employees> expected = Lists.newArrayList(employees);

    assertThat(actual, is(expected));
  }

  @Test
  public void parseUnknownFile() throws Exception {
    List<Employee> actual = domParser.parseList(null, Employees.class);
    List<Employee> expected = Lists.newArrayList();

    assertThat(actual, is(expected));
  }
}