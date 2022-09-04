package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    public void init(Servlet config) throws ClassNotFoundException {
        storage = Config.getInstance().getStorage();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");


        PrintWriter writer = response.getWriter();
        writer.println("<table><tr><th>UUID</th><th>Full Name</th></tr>");

        List<Resume> resumes = storage.getAllSorted();
        for (Resume r : resumes) {
            writer.println("<tr><td>" + r.getUuid() + "</td><td>" + r.getFullName() + "</td></tr>");
        }

        writer.println("</table>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

    }
}
