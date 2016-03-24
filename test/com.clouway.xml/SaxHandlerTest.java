package com.clouway.xml;



import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class SaxHandlerTest {

    @Test
    public void happyPath() throws ParserConfigurationException, SAXException, IOException {
        List<Employee> expectedElements = getExpectedElements();

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        SaxHandler handler = new SaxHandler<Employee>(Employee.class);
        parser.parse(ClassLoader.getSystemResourceAsStream("Employees.xml"), handler);

        assertThat(handler.elements, is(equalTo(expectedElements)));
    }

    private List<Employee> getExpectedElements() {
        List<Employee> expectedElements = new ArrayList<>();
        Address addressOne = new Address();
        addressOne.setStreet("washington str.");
        addressOne.setStreetNo(2);
        Employee employeeOne = new Employee();
        employeeOne.setFirstName("George");
        employeeOne.setAge(56);
        employeeOne.setAddress(addressOne);
        expectedElements.add(employeeOne);

        Address addressTwo = new Address();
        addressTwo.setStreet("benn str.");
        addressTwo.setStreetNo(1);

        Employee employeeTwo = new Employee();
        employeeTwo.setFirstName("Bill");
        employeeTwo.setAge(60);
        employeeTwo.setAddress(addressTwo);
        expectedElements.add(employeeTwo);
        return expectedElements;
    }

}
