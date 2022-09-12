package ru.javawebinar.basejava.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String website;
    private List<Period> periods = new ArrayList<>();

    public Organization() {
        this.name = "";
        this.website = "";
        this.periods.add(new Period());
    }

    public Organization(String name) {
        this.name = Objects.requireNonNull(name, "Name must not be null");
    }

    public Organization(String name, String website) {
        this(name);
        Objects.requireNonNull(website);
        this.website = website;
    }

    public Organization(String name, String website, List<Period> periods) {
        this(name, website);
        this.periods = Objects.requireNonNull(periods);
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) && Objects.equals(website, that.website) && Objects.equals(periods,
                that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, periods);
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
