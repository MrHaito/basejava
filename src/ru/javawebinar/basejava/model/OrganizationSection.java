package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends Section {
    List<Organization> organizations = new ArrayList<>();

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public String toString() {
        String results = "";
        for (Organization organization : organizations) {
            results += organization.getName() + "\n" + organization + "\n";
        }
        return results;
    }
}
