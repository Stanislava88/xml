package com.clouway.xml;


import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.clouway.xml.Address.aNewAddress;
import static com.clouway.xml.Employee.aNewEmployee;
import static com.clouway.xml.Employer.aNewEmployer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class SaxHandlerTest {

    @Test
    public void happyPath() throws ParserConfigurationException, SAXException, IOException {
        List<Employee> expectedElements = new ArrayList<>();
        Address addressOne = aNewAddress().street("washington str.").streetNo(2).build();
        Employer employer = aNewEmployer().name("US nation").startDate(Date.valueOf("1945-11-12")).endDate(Date.valueOf("1954-11-10")).build();
        Employee employeeOne = aNewEmployee().firstName("George").age(56).address(addressOne).employer(employer).build();
        expectedElements.add(employeeOne);

        Address addressTwo = aNewAddress().street("benn str.").streetNo(1).build();
        Employer employerTwo = aNewEmployer().name("Microsoft").startDate(Date.valueOf("1990-12-12")).endDate(Date.valueOf("2016-4-5")).build();
        Employee employeeTwo = aNewEmployee().firstName("Bill").age(60).address(addressTwo).employer(employerTwo).build();
        expectedElements.add(employeeTwo);

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        SaxHandler handler = new SaxHandler<Employee>(Employee.class);
        parser.parse(ClassLoader.getSystemResourceAsStream("Employees.xml"), handler);

        assertThat(handler.getElements(), is(equalTo(expectedElements)));
    }

}
