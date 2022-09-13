<jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName} <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
        <h2>${type.title}</h2>
        <c:choose>
            <c:when test="${type == 'PERSONAL' or type == 'OBJECTIVE'}">
                <p>${section}</p>
            </c:when>
            <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                <c:forEach var="string" items="<%=((ListSection) section).getStrings()%>">
                    <ul>
                        <li>${string}</li>
                    </ul>
                </c:forEach>
            </c:when>
            <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                <c:forEach var="organization"
                           items="<%=((OrganizationSection) section).getOrganizations()%>">
                    <c:choose>
                        <c:when test="${empty organization.website}">
                            <h4>${organization.name}</h4>
                        </c:when>
                        <c:otherwise>
                            <h4><a href="${organization.website}">${organization.name}</a></h4>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="period"
                               items="${organization.periods}">
                        <c:set var="startDate" value="${period.startDate}"/>
                        <c:set var="endDate" value="${period.endDate}"/>
                        <c:choose>
                            <c:when test="${startDate != null || endDate != null}">
                                <p><strong>Период работы</strong>: ${period.startDate} - ${period.endDate}</p>
                            </c:when>
                        </c:choose>
                        <p>${period.position}</p>
                        <p>${period.description}</p>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>