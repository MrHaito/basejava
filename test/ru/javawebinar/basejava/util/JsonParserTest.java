package ru.javawebinar.basejava.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.TextSection;

import static ru.javawebinar.basejava.TestData.RESUME_1;

class JsonParserTest {

    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assertions.assertEquals(RESUME_1, resume);
    }

    @Test
    void write() {
        Section section = new TextSection("Objective1");
        String json = JsonParser.write(section, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        Assertions.assertEquals(section, section2);
    }
}