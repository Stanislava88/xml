package com.clouway.xml;


import java.util.Date;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Employer {
  private final String name;
  private final Date startDate;
  private final Date endDate;

  public Employer() {
    name = null;
    startDate = null;
    endDate = null;
  }

  public Employer(String name, Date startDate, Date endDate) {
    this.name = name;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Employer employer = (Employer) o;

    if (name != null ? !name.equals(employer.name) : employer.name != null) return false;
    if (startDate != null ? !startDate.equals(employer.startDate) : employer.startDate != null) return false;
    return !(endDate != null ? !endDate.equals(employer.endDate) : employer.endDate != null);

  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
    result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
    return result;
  }
}
