package ru.javawebinar.basejava.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<Organization> organizations = new ArrayList<>();

    public OrganizationSection() {}

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        this.organizations = Objects.requireNonNull(organizations, "organizations must not be null");;
    }

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
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
