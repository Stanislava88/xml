package com.clouway.domparser.core;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Address {
  private String street;
  private String streetNumber;
  private String section;
  private String city;

  public Address(String street, String streetNumber, String section, String city) {
    this.street = street;
    this.streetNumber = streetNumber;
    this.section = section;
    this.city = city;
  }

  public Address() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Address address = (Address) o;

    if (street != null ? !street.equals(address.street) : address.street != null) return false;
    if (streetNumber != null ? !streetNumber.equals(address.streetNumber) : address.streetNumber != null) return false;
    if (section != null ? !section.equals(address.section) : address.section != null) return false;
    return city != null ? city.equals(address.city) : address.city == null;

  }

  @Override
  public int hashCode() {
    int result = street != null ? street.hashCode() : 0;
    result = 31 * result + (streetNumber != null ? streetNumber.hashCode() : 0);
    result = 31 * result + (section != null ? section.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Address{" +
            "street='" + street + '\'' +
            ", streetNumber='" + streetNumber + '\'' +
            ", section='" + section + '\'' +
            ", city='" + city + '\'' +
            '}';
  }
}
