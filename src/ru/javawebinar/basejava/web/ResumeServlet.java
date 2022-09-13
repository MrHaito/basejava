package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        OrganizationSection organizationSection;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view", "edit" -> r = storage.get(uuid);
            case "add" -> {
                r = new Resume();
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(("view".equals(action) ? "WEB-INF/jsp/view.jsp" : "WEB-INF/jsp/edit.jsp")).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean newResume = (uuid == null || uuid.length() == 0);
        Resume r;
        if (!newResume) {
            r = storage.get(uuid);
        } else {
            r = new Resume(fullName);
        }
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);

            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            String[] values = request.getParameterValues(type.name());
            if (value != null) {
                switch (type) {
                    case OBJECTIVE, PERSONAL -> r.addSection(type, new TextSection(value));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = new ListSection();
                        List<String> strings = new ArrayList<>(Arrays.asList(value.split("\n")));
                        for (String string : strings) {
                            if (!string.isBlank()) {
                                listSection.addDescription(string);
                            }
                        }
                        r.addSection(type, listSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        int orgsCount = 0;
                        List<Organization> orgs = new ArrayList<>();
                        String[] webs = request.getParameterValues(type.name() + "web");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (name.length() > 0) {
                                orgsCount += 1;

                                String[] positions = request.getParameterValues((type.name() + i) + "period_position");
                                String[] descriptions = request.getParameterValues((type.name() + i) + "period_description");
                                String[] startsDates = request.getParameterValues((type.name() + i) + "period_startDate");
                                String[] endDates = request.getParameterValues((type.name() + i) + "period_endDate");
                                List<Period> periods = new ArrayList<>();

                                for (int j = 0; j < positions.length; j++) {
                                    String start = "01/" + startsDates[j];
                                    String end = "01/" + endDates[j];
                                    LocalDate startDate = (start.length() < 4) ? null : LocalDate.parse(start, formatter);
                                    LocalDate endDate = (end.length() < 4) ? null : LocalDate.parse(end, formatter);

                                    Period period = new Period(startDate, endDate, positions[j], descriptions[j]);
                                    periods.add(period);
                                }
                                if (positions[positions.length - 1].length() > 0 || descriptions[descriptions.length - 1].length() > 0 || startsDates[startsDates.length - 1].length() > 0 || endDates[endDates.length - 1].length() > 0) {
                                    periods.add(new Period());
                                }
                                Organization organization = new Organization(name, webs[i], periods);
                                orgs.add(organization);
                            }
                        }
                        List<Organization> currentOrganisations = (r.getSections(type) == null) ? null :
                                ((OrganizationSection) r.getSections(type)).getOrganizations();
                        if (currentOrganisations != null && currentOrganisations.size() == orgsCount) {
                            orgs.add(new Organization());
                        }
                        if (orgs.size() == 0) {
                            orgs.add(new Organization("", "", new Period()));
                        }
                        r.addSection(type, new OrganizationSection(orgs));
                    }
                }
            } else {
                r.getSections().remove(type);
            }
        }
        if (!newResume) {
            storage.update(r);
        } else {
            storage.save(r);
        }
        response.sendRedirect("resume");
    }
}
