package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StorageStrategy {
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            sectionWriter(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = r.getSections();
            sectionWriter(sections.entrySet(), dos, entry -> {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        dos.writeUTF(((TextSection) r.getSections().get(sectionType)).getDescription());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> strings = ((ListSection) r.getSections().get(sectionType)).getStrings();
                        sectionWriter(strings, dos, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizationList =
                                ((OrganizationSection) r.getSections().get(sectionType)).getOrganizations();
                        sectionWriter(organizationList, dos, organization -> {
                            dos.writeUTF(organization.getName());
                            dos.writeUTF(organization.getWebsite());
                            List<Period> periods = organization.getPeriods();
                            sectionWriter(periods, dos, period -> {
                                dos.writeUTF(period.getStartDate().toString());
                                dos.writeUTF(period.getEndDate().toString());
                                dos.writeUTF(period.getPosition());
                                dos.writeUTF(period.getDescription() != null ? period.getDescription() : "null");
                            });
                        });
                    }
                }
            });
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            sectionReader(dis, () -> {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });
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
                        ListSection listSection = new ListSection();
                        sectionReader(dis, () -> {
                            listSection.addDescription(dis.readUTF());
                            resume.addSection(sectionType, listSection);
                        });
                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganizationSection organizationSection = new OrganizationSection();
                        sectionReader(dis, () -> {
                            Organization organization = new Organization(dis.readUTF(), dis.readUTF());
                            sectionReader(dis, () -> {
                                LocalDate localStartDate = LocalDate.parse(dis.readUTF());
                                LocalDate localEndDate = LocalDate.parse(dis.readUTF());
                                String position = dis.readUTF();
                                String description = dis.readUTF();
                                Period period = description.equals("null") ? new Period(localStartDate, localEndDate,
                                        position) : new Period(localStartDate, localEndDate, position, description);
                                organization.addPeriod(period);
                            });
                            organizationSection.addOrganization(organization);
                        });
                        resume.addSection(sectionType, organizationSection);
                    }
                }
            }
            return resume;
        }
    }

    private <T> void sectionWriter(Collection<T> collection, DataOutputStream dos, consumerWriter<T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            consumer.write(t);
        }
    }

    private <T> void sectionReader(DataInputStream dis, consumerReader<T> consumer) throws IOException {
        int size = dis.readInt();
        for (int j = 0; j < size; j++) {
            consumer.read();
        }
    }


}
