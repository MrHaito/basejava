package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел.: "),
    SKYPE("Skype: "),
    EMAIL("Почта: "),
    PROFILE_LINKEDIN(""),
    PROFILE_GITHUB(""),
    PROFILE_STACKOVERFLOW(""),
    HOMEPAGE("");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
