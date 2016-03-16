package com.clouway.firsttask;

/**
 * Created by clouway on 16-3-8.
 */
public class Employer {
    public String name;
    public String startDate;
    public String endDate;

    public Employer(String name, String startDate, String endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Employer() {
    }

    @Override
    public String toString() {
        return "Employer{" +
                "name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
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
