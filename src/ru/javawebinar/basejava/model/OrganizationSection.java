package ru.javawebinar.basejava.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

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
