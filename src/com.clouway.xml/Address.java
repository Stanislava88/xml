package com.clouway.xml;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Address {
    private String street;
    private int streetNo;

    public Address() {
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNo(int streetNo) {
        this.streetNo = streetNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (streetNo != address.streetNo) return false;
        return !(street != null ? !street.equals(address.street) : address.street != null);

    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + streetNo;
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", streetNo=" + streetNo +
                '}';
    }
}
