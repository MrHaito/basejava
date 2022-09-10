<jsp:useBean id="resume" scope="request" type="ru.javawebinar.basejava.model.Resume"/>
<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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
        <dl>
            <c:if test="${section_type == 'PERSONAL' or section_type == 'OBJECTIVE'}">
            <dt>${section_type.title}</dt>
            <dd><input type="text" name="${section_type.name()}" size="30"
                       value="${resume.getSections(section_type)}"/></dd>
            </c:if>
            <c:if test="${section_type == 'ACHIEVEMENT' or section_type == 'QUALIFICATIONS'}">
                <dt>${section_type.title}</dt>
                <dd><textarea name="${section_type.name()}" cols="100"
                              rows="20">${resume.getSections(section_type)}</textarea>
            </c:if>
        </dl>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
