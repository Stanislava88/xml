package com.clouway.xml;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Address {
  private final String street;
  private final Integer streetNo;

  public Address() {
    street = null;
    streetNo = null;
  }

  public Address(String street, Integer streetNo) {
    this.street = street;
    this.streetNo = streetNo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Address address = (Address) o;

    if (street != null ? !street.equals(address.street) : address.street != null) return false;
    return !(streetNo != null ? !streetNo.equals(address.streetNo) : address.streetNo != null);

  }

  @Override
  public int hashCode() {
    int result = street != null ? street.hashCode() : 0;
    result = 31 * result + (streetNo != null ? streetNo.hashCode() : 0);
    return result;
  }
}
