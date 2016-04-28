package com.clouway.saxparser.core;

import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class Addresses {
  private List<Address> addresses;

  public Addresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public Addresses() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Addresses addresses1 = (Addresses) o;

    return addresses != null ? addresses.equals(addresses1.addresses) : addresses1.addresses == null;

  }

  @Override
  public int hashCode() {
    return addresses != null ? addresses.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Addresses{" +
            "addresses=" + addresses +
            '}';
  }
}
