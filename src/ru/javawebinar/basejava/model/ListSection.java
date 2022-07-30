package ru.javawebinar.basejava.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> strings = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(String... strings) {
        this(Arrays.asList(strings));
    }

    public ListSection(List<String> strings) {
        this.strings =  Objects.requireNonNull(strings, "items must not be null");
    }

    public List<String> getStrings() {
        return strings;
    }

    public void addDescription(String text) {
        strings.add(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return getStrings().equals(that.getStrings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStrings());
    }

    @Override
    public String toString() {
        StringBuilder results = new StringBuilder();
        for (String string : strings) {
            results.append(string).append("\n");
        }
        return results.toString();
    }
}
