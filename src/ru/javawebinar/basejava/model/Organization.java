package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    String name;
    String website;
    List<Period> periods = new ArrayList<>();

    public void addPeriod(Period period) {
        periods.add(period);
    }

    public Organization(String name) {
        this.name = name;
    }

    public Organization(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String results = "";
        for (Period period : periods) {
            results += period.toString() + "\n";
        }
        return results;
    }
}
