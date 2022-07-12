package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {
    private List<String> description = new ArrayList<>();

    public void addDescription(String text) {
        description.add(text);
    }

    @Override
    public String toString() {
        String results = "";
        for (String string : description) {
            results += "\u2023" + string.toString() + "\n";
        }
        return results;
    }
}
