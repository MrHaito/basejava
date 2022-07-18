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
        StringBuilder results = new StringBuilder();
        for (Organization organization : organizations) {
            results.append(organization.getName()).append("\n").append(organization).append("\n");
        }
        return results.toString();
    }
}
