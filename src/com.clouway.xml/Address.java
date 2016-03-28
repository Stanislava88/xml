package com.clouway.xml;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class Address {

    public static final class Builder {
        private String street;
        private Integer streetNo;

        private Builder() {
        }

        public Builder street(String val) {
            street = val;
            return this;
        }

        public Builder streetNo(Integer val) {
            streetNo = val;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }

    public static Builder aNewAddress() {
        return new Builder();
    }

    private final String street;
    private final Integer streetNo;

    public Address() {
        street = null;
        streetNo = null;
    }

    private Address(Builder builder) {
        street = builder.street;
        streetNo = builder.streetNo;
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
