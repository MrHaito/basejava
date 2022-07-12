package ru.javawebinar.basejava.model;

public class TextSection extends Section {
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
