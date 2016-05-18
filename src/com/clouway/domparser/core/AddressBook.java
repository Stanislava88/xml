package com.clouway.domparser.core;

import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class AddressBook {
  private List<Address> addresses;

  public AddressBook() {
  }

  public AddressBook(List<Address> addresses) {
    this.addresses = addresses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AddressBook addresses1 = (AddressBook) o;

    return addresses != null ? addresses.equals(addresses1.addresses) : addresses1.addresses == null;

  }

  @Override
  public int hashCode() {
    return addresses != null ? addresses.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "AddressBook{" +
            "addresses=" + addresses +
            '}';
  }
}
