<jsp:useBean id="resume" scope="request" type="ru.javawebinar.basejava.model.Resume"/>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Резюме ${resume.fullName}</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text"
                       required
                       name="fullName"
                       size="50"
                       pattern="^[^\s]+(\s.*)?$"
                       value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContacts(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="section_type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSections(section_type)}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
            <%--            <jsp:useBean id="organizations" type="ru.javawebinar.basejava.model.OrganizationSection"/>--%>
            <h2><a>${section_type.title}</a></h2>
            <c:choose>
                <c:when test="${section_type == 'PERSONAL' or section_type == 'OBJECTIVE'}">
                    <input type="text" name="${section_type.name()}" size="102"
                           value="${section}"/>
                </c:when>
                <c:when test="${section_type == 'ACHIEVEMENT' or section_type == 'QUALIFICATIONS'}">
                    <textarea name="${section_type.name()}" cols="100" rows="20">${section}</textarea>
                </c:when>
                <c:when test="${section_type == 'EXPERIENCE' or section_type == 'EDUCATION'}">
                    <%--                    <c:forEach var="organization" items="${organizations.organizations}">--%>
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>">
                        <dl>
                            <dt>Место работы:</dt>
                            <dd><input type="text" name="${section_type}" size="30"
                                       value="${organization.name}"/></dd>
                        </dl>
                        <dl>
                            <dt>Web-сайт:</dt>
                            <dd><input type="text" name="${section_type}web" size="30"
                                       value="${organization.website}"/></dd>
                        </dl>
                        <c:forEach var="period" items="${organization.periods}">
                            <dl>
                                <dt>Начало работы:</dt>
                                <dd><input type="text"
                                           name="${section_type.name()}period_startDate"
                                           size="30"
                                           value="${period.startDate.format(DateTimeFormatter.ofPattern("MM/yyyy"))}"
                                           pattern="[0-9/]{7}"
                                           placeholder="MM/YYYY"/></dd>
                            </dl>
                            <dl>
                                <dt>Конец работы:</dt>
                                <dd><input type="text"
                                           name="${section_type.name()}period_endDate"
                                           size="30"
                                           value="${period.endDate.format(DateTimeFormatter.ofPattern("MM/yyyy"))}"
                                           pattern="[0-9/]{7}"
                                           placeholder="MM/YYYY"/></dd>
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name="${section_type.name()}period_position" size="30"
                                           value="${period.position}"/></dd>
                            </dl>
                            <dl>
                                <dt>Описание:</dt>
                                <dd><textarea name="${section_type.name()}period_description" cols="100"
                                              rows="20">${period.description}</textarea></dd>
                            </dl>
                        </c:forEach>
                        <br/>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
