package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StorageStrategy {
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        getSectionName(dos, entry);
                        dos.writeUTF(((TextSection) r.getSections().get(sectionType)).getDescription());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        getSectionName(dos, entry);
                        List<String> strings = ((ListSection) r.getSections().get(sectionType)).getStrings();
                        dos.writeInt(strings.size());
                        for (String string : strings) {
                            dos.writeUTF(string);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        getSectionName(dos, entry);
                        List<Organization> organizationList = ((OrganizationSection) r.getSections().get(sectionType)).getOrganizations();
                        dos.writeInt(organizationList.size());
                        for (Organization organization : organizationList) {
                            dos.writeUTF(organization.getName());
                            dos.writeUTF(organization.getWebsite());
                            List<Period> periods = organization.getPeriods();
                            dos.writeInt(periods.size());
                            for (Period period : periods) {
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                                dos.writeUTF(period.getPosition());
                                if (period.getDescription() != null) {
                                    dos.writeUTF(period.getDescription());
                                } else {
                                    dos.writeUTF("null");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int contactSize = dis.readInt();
            for (int i = 0; i < contactSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection textSection = new TextSection();
                        textSection.setDescription(dis.readUTF());
                        resume.addSection(sectionType, textSection);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int listSectionSize = dis.readInt();
                        ListSection listSection = new ListSection();
                        for (int j = 0; j < listSectionSize; j++) {
                            listSection.addDescription(dis.readUTF());
                            resume.addSection(sectionType, listSection);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int organizationListSize = dis.readInt();
                        OrganizationSection organizationSection = new OrganizationSection();
                        for (int k = 0; k < organizationListSize; k++) {
                            Organization organization = new Organization(dis.readUTF(), dis.readUTF());
                            int periodSize = dis.readInt();
                            for (int l = 0; l < periodSize; l++) {
                                LocalDate localStartDate = LocalDate.parse(dis.readUTF());
                                LocalDate localEndDate = LocalDate.parse(dis.readUTF());
                                String position = dis.readUTF();
                                String description = dis.readUTF();
                                Period period;
                                if (description.equals("null")) {
                                    period = new Period(localStartDate, localEndDate, position);
                                } else {
                                    period = new Period(localStartDate, localEndDate, position, description);
                                }
                                organization.addPeriod(period);
                            }
                            organizationSection.addOrganization(organization);
                        }
                        resume.addSection(sectionType, organizationSection);
                    }
                }
            }
            return resume;
        }
    }

    private void getSectionName(DataOutputStream dos, Map.Entry<SectionType, Section> entry) throws IOException {
        dos.writeUTF(entry.getKey().name());
    }
}
