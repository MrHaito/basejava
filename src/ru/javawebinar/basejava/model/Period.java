package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public Period(LocalDate startDate, LocalDate endDate, String position) {
        this.startDate = Objects.requireNonNull(startDate, "StartDate must not be null");;
        this.endDate = Objects.requireNonNull(endDate, "EndDate must not be null");
        this.position = Objects.requireNonNull(position, "Position must not be null");
    }

    public Period(LocalDate startDate, LocalDate endDate, String position, String description) {
        this(startDate, endDate, position);
        Objects.requireNonNull(description);
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPosition() {
        return position;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (getStartDate() != null ? !getStartDate().equals(period.getStartDate()) : period.getStartDate() != null)
            return false;
        if (getEndDate() != null ? !getEndDate().equals(period.getEndDate()) : period.getEndDate() != null)
            return false;
        if (getPosition() != null ? !getPosition().equals(period.getPosition()) : period.getPosition() != null)
            return false;
        return getDescription() != null ? getDescription().equals(period.getDescription()) : period.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getStartDate() != null ? getStartDate().hashCode() : 0;
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        result = 31 * result + (getPosition() != null ? getPosition().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Period{" + "startDate='" + startDate + '\'' + ", endDate='" + endDate + '\'' + ", position='" + position + '\'' + ", description='" + description + '\'' + '}';
    }
}
