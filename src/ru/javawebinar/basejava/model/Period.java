package ru.javawebinar.basejava.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate startDate;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate endDate;
    private String position;
    private String description;

    public Period() {
    }

//    public Period(LocalDate startDate, LocalDate endDate, String position) {
//        this.startDate = Objects.requireNonNull(startDate, "StartDate must not be null");;
//        this.endDate = Objects.requireNonNull(endDate, "EndDate must not be null");
//        this.position = Objects.requireNonNull(position, "Position must not be null");
//    }

    public Period(LocalDate startDate, LocalDate endDate, String position, String description) {
        this.startDate = Objects.requireNonNull(startDate, "StartDate must not be null");;
        this.endDate = Objects.requireNonNull(endDate, "EndDate must not be null");
        this.position = Objects.requireNonNull(position, "Position must not be null");
        this.description = description != null ? description : "";
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
        return Objects.equals(startDate, period.startDate) && Objects.equals(endDate, period.endDate) && Objects.equals(position, period.position) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, position, description);
    }

    @Override
    public String toString() {
        return "Period{" + "startDate='" + startDate + '\'' + ", endDate='" + endDate + '\'' + ", position='" + position + '\'' + ", description='" + description + '\'' + '}';
    }
}
