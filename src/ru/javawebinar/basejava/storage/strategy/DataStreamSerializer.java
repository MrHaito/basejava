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
                    case PERSONAL, OBJECTIVE ->
                            dos.writeUTF(((TextSection) r.getSections().get(sectionType)).getDescription());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            sectionWriter(((ListSection) r.getSections().get(sectionType)).getStrings(), dos,
                                    dos::writeUTF);
                    case EXPERIENCE, EDUCATION ->
                            sectionWriter(((OrganizationSection) r.getSections().get(sectionType)).getOrganizations()
                                    , dos, organization -> {
                                dos.writeUTF(organization.getName());
                                dos.writeUTF(organization.getWebsite());
                                sectionWriter(organization.getPeriods(), dos, period -> {
                                    dos.writeUTF(period.getStartDate().toString());
                                    dos.writeUTF(period.getEndDate().toString());
                                    dos.writeUTF(period.getPosition());
                                    dos.writeUTF(period.getDescription());
                                });
                            });
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
            sectionReader(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            resume.addSection(sectionType, new ListSection(returnList(dis, dis::readUTF)));
                    case EXPERIENCE, EDUCATION ->
                            resume.addSection(sectionType, new OrganizationSection(returnList(dis,
                                    () -> new Organization(dis.readUTF(), dis.readUTF(), returnList(dis,
                                            () -> new Period(LocalDate.parse(dis.readUTF()),
                                                    LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()))))));
                }
            });
            return resume;
        }
    }

    private <T> List<T> returnList(DataInputStream dis, consumerList<T> consumer) throws IOException {
        List<T> list = new ArrayList<>();
        int size = dis.readInt();
        for (int j = 0; j < size; j++) {
            list.add(consumer.listReturn());
        }
        return list;
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
