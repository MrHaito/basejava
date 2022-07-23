package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    String name;
    String website;
    List<Period> periods = new ArrayList<>();

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public Organization(String name) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
    }

    public Organization(String name, String website) {
        this(name);
        Objects.requireNonNull(website);
        this.website = website;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (!Objects.equals(website, that.website)) return false;
        return Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder results = new StringBuilder();
        for (Period period : periods) {
            results.append(period.toString()).append("\n");
        }
        return results.toString();
    }
}
