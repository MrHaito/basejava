package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {
    private final List<String> strings = new ArrayList<>();

    public List<String> getStrings() {
        return strings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return getStrings() != null ? getStrings().equals(that.getStrings()) : that.getStrings() == null;
    }

    @Override
    public int hashCode() {
        return getStrings() != null ? getStrings().hashCode() : 0;
    }

    public void addDescription(String text) {
        strings.add(text);
    }

    @Override
    public String toString() {
        String results = "";
        for (String string : strings) {
            results += string + "\n";
        }
        return results;
    }
}
