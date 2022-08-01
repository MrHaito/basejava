package ru.javawebinar.basejava.storage.strategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
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
                        dos.writeUTF(entry.getKey().name());
                        TextSection textSection = (TextSection) r.getSections().get(sectionType);
                        dos.writeUTF(textSection.getDescription());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        dos.writeUTF(entry.getKey().name());
                        ListSection listSection = (ListSection) r.getSections().get(sectionType);
                        List<String> list = listSection.getStrings();
                        dos.writeInt(list.size());
                        for (String string : list) {
                            dos.writeUTF(string);
                        }
                    }
                }
            }
            //TODO implements sections
        }
    }

    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sizeSection = dis.readInt();
            for (int i = 0; i < sizeSection; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        String description = dis.readUTF();
                        TextSection textSection = new TextSection();
                        textSection.setDescription(description);
                        resume.addSection(sectionType, textSection);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int sizeListSection = dis.readInt();
                        ListSection listSection = new ListSection();
                        for (int j = 0; j < sizeListSection; j++) {
                            String description = dis.readUTF();
                            listSection.addDescription(description);
                            resume.addSection(sectionType, listSection);
                        }
                    }
                }
            }
            //TODO implements sections
            return resume;
        }
    }
}
