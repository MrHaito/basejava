package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contacts = new HashMap<>();
    private final Map<SectionType, Section> sections = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    public void addContact(ContactType type, String value) {
         contacts.put(type, value);
    }

    public void addSection(SectionType type, Section value) {
        sections.put(type, value);
    }

    public void getAllContacts() {
        for (ContactType contactType : ContactType.values()) {
            System.out.println(contactType + contacts.get(contactType));
        }
        System.out.println(" ");
    }

    public void getAllSection() {
        for (SectionType sectionType : SectionType.values()) {
            System.out.println(sectionType + "\n" + sections.get(sectionType));
        }
    }
}
