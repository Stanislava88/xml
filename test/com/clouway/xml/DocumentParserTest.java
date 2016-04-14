package com.clouway.xml;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.clouway.xml.Employee.aNewEmployee;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class DocumentParserTest {

  @Test
  public void happyPath() throws IOException, SAXException, ParserConfigurationException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
    List<Employee> expectedElements = new ArrayList<>();
    Address addressOne = new Address("washington str.", 2);
    Employer employer = new Employer("US nation", Date.valueOf("1945-11-12"), Date.valueOf("1954-11-10"));
    Employee employeeOne = aNewEmployee().firstName("George").age(56).address(addressOne).employer(employer).build();
    expectedElements.add(employeeOne);

    Address addressTwo = new Address("benn str.", 1);
    Employer employerTwo = new Employer("Microsoft", Date.valueOf("1990-12-12"), Date.valueOf("2016-4-5"));
    Employee employeeTwo = aNewEmployee().firstName("Bill").age(60).address(addressTwo).employer(employerTwo).build();
    expectedElements.add(employeeTwo);

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse("xml/Employees.xml");
    doc.getDocumentElement().normalize();
    DocumentParser docParser = new DocumentParser<Employee>(Employee.class);
    List actualElements = docParser.parse(doc);
    assertThat(actualElements, is(equalTo(expectedElements)));
  }
}
