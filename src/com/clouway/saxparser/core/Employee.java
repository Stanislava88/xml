package com.clouway.saxparser.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Employee {
  private String firstName;
  private String lastName;
  private String age;
  private String position;
  private Employer Employer;
  private Addresses addresses;

  public Employee() {
  }

  public Employee(String firstName, String lastName, String age, String position, Employer employer, Addresses addresses) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.position = position;
    this.Employer = employer;
    this.addresses = addresses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Employee employee = (Employee) o;

    if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
    if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null) return false;
    if (age != null ? !age.equals(employee.age) : employee.age != null) return false;
    if (position != null ? !position.equals(employee.position) : employee.position != null) return false;
    if (Employer != null ? !Employer.equals(employee.Employer) : employee.Employer != null) return false;
    return addresses != null ? addresses.equals(employee.addresses) : employee.addresses == null;

  }

  @Override
  public int hashCode() {
    int result = firstName != null ? firstName.hashCode() : 0;
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (age != null ? age.hashCode() : 0);
    result = 31 * result + (position != null ? position.hashCode() : 0);
    result = 31 * result + (Employer != null ? Employer.hashCode() : 0);
    result = 31 * result + (addresses != null ? addresses.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Employee{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", age=" + age +
            ", position='" + position + '\'' +
            ", Employer=" + Employer +
            ", addresses=" + addresses +
            '}';
  }
}
