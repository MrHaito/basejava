<jsp:useBean id="resume" scope="request" type="ru.javawebinar.basejava.model.Resume"/>
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
    <h2>${resume.fullName} <a href="resume?uuid=${resume.uuid}&action=edit"><img
            src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>

        <h3><%=sectionEntry.getKey().getTitle()%></h3>
        <p>
        <c:set var="section" scope="session" value="${sectionEntry.key}"/>
        <c:if test="${section == 'PERSONAL' or section == 'OBJECTIVE'}">
            <p><%=sectionEntry.getValue()%></p>
        </c:if>
        <c:if test="${section == 'ACHIEVEMENT' or section == 'QUALIFICATIONS'}">
            <textarea cols="100" rows="20"><%=resume.getSections().get(sectionEntry.getKey())%></textarea>
        </c:if>
        <c:if test="${section == 'EXPERIENCE' or section == 'EDUCATION'}">
            <textarea cols="100" rows="20"><%=resume.getSections().get(sectionEntry.getKey())%></textarea>
        </c:if>
        </p>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
