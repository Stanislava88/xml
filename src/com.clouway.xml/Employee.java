package com.clouway.xml;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Employee {
  public static final class Builder {
    private String firstName;
    private Integer age;
    private Address address;
    private Employer employer;

    private Builder() {
    }

    public Builder firstName(String val) {
      firstName = val;
      return this;
    }

    public Builder age(Integer val) {
      age = val;
      return this;
    }

    public Builder address(Address val) {
      address = val;
      return this;
    }

    public Builder employer(Employer val) {
      employer = val;
      return this;
    }

    public Employee build() {
      return new Employee(this);
    }
  }

  public static Builder aNewEmployee() {
    return new Builder();
  }

  private final String firstName;
  private final Integer age;
  private final Address address;
  private final Employer employer;

  public Employee() {
    firstName = null;
    age = null;
    address = null;
    employer = null;
  }

  private Employee(Builder builder) {
    firstName = builder.firstName;
    age = builder.age;
    address = builder.address;
    employer = builder.employer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Employee employee = (Employee) o;

    if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
    if (age != null ? !age.equals(employee.age) : employee.age != null) return false;
    if (address != null ? !address.equals(employee.address) : employee.address != null) return false;
    return !(employer != null ? !employer.equals(employee.employer) : employee.employer != null);

  }

  @Override
  public int hashCode() {
    int result = firstName != null ? firstName.hashCode() : 0;
    result = 31 * result + (age != null ? age.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (employer != null ? employer.hashCode() : 0);
    return result;
  }
}
