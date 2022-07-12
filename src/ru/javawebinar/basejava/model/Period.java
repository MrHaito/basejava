package ru.javawebinar.basejava.model;

public class Period {
    private String startDate;
    private String endDate;
    private String position;
    private String description;

    public Period(String startDate, String endDate, String position) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
    }

    public Period(String startDate, String endDate, String position, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    @Override
    public String toString() {
        if (description == null) {
            return startDate + " - " +
                    endDate + "\n" +
                    position;
        }
        return startDate + " - " +
                endDate + "\n" +
                position + "\n" +
                description;
    }
}
