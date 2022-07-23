package ru.javawebinar.basejava.model;

import java.io.Serial;

public class TextSection extends Section {
    @Serial
    private static final long serialVersionUID = 1L;

    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return getDescription() != null ? getDescription().equals(that.getDescription()) : that.getDescription() == null;
    }

    @Override
    public int hashCode() {
        return getDescription() != null ? getDescription().hashCode() : 0;
    }

    @Override
    public String toString() {
        return description;
    }
}
