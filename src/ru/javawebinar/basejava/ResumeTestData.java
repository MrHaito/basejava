package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.time.Month;

public class ResumeTestData {

    public static void main(String[] args) {

    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.PROFILE_LINKEDIN, "Профиль LinkedIn");
        resume.addContact(ContactType.PROFILE_GITHUB, "Профиль GitHub");
        resume.addContact(ContactType.PROFILE_STACKOVERFLOW, "Профиль StackOverFlow");
        resume.addContact(ContactType.HOMEPAGE, "Домашняя страница");


        SectionType objective = SectionType.OBJECTIVE;
        TextSection objective_description = new TextSection();
        objective_description.setDescription("Ведущий стажировок и " + "корпоративного обучения по Java Web и " +
                "Enterprise " + "технологиям");

        SectionType personal = SectionType.PERSONAL;
        TextSection personal_description = new TextSection();
        personal_description.setDescription("Аналитический склад ума, " + "сильная" + " логика, креативность, " +
                "инициативность. " + "Пурист кода и " + "архитектуры.");

        SectionType achievement = SectionType.ACHIEVEMENT;
        ListSection achievement_description = new ListSection();
        achievement_description.addDescription("Организация команды и успешная реализация Java проектов для " +
                "сторонних" + " заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система " +
                "мониторинга показателей" + " спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, " +
                "многомодульный Spring Boot + Vaadin " + "проект для комплексных DIY смет");
        achievement_description.addDescription("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java" + " Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы " +
                "(JAX-RS/SOAP). " + "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение " + "проектов. Более 3500 " + "выпускников.");
        achievement_description.addDescription("Реализация двухфакторной аутентификации для онлайн платформы " +
                "управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement_description.addDescription("Налаживание процесса разработки и непрерывной интеграции ERP системы" + "River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на " + "стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " + "интеграция CIFS/SMB java сервера.");
        achievement_description.addDescription("Реализация c нуля Rich Internet Application приложения на стеке " +
                "технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для " +
                "алгоритмического" + " трейдинга.");
        achievement_description.addDescription("Создание JavaEE фреймворка для отказоустойчивого взаимодействия " +
                "слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики " +
                "сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента " +
                "для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement_description.addDescription("Реализация протоколов по приему платежей всех основных платежных " +
                "системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        SectionType qualifications = SectionType.QUALIFICATIONS;
        ListSection qualifications_description = new ListSection();
        qualifications_description.addDescription("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, " +
                "WebLogic, WSO2");
        qualifications_description.addDescription("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications_description.addDescription("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), " + "H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualifications_description.addDescription("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, " +
                "Groovy");
        qualifications_description.addDescription("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications_description.addDescription("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor,"
                + " " + " MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, " + "GWT (SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium " + "(htmlelements).");
        qualifications_description.addDescription("Python: Django.");
        qualifications_description.addDescription("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications_description.addDescription("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications_description.addDescription("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, " +
                "JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, " + "ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications_description.addDescription("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualifications_description.addDescription("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, " +
                "JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualifications_description.addDescription("Отличное знание и опыт применения концепций ООП, SOA, шаблонов " + "проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications_description.addDescription("Родной русский, английский \"upper intermediate\"");

        SectionType experience = SectionType.EXPERIENCE;
        OrganizationSection experience_description = new OrganizationSection();
        Organization organization = new Organization("Java Online Projects", "http://javaops.ru/");
        organization.addPeriod(new Period(2013, Month.OCTOBER, 2022, Month.AUGUST, "Автор проекта.", "Создание, " +
                "организация и проведение Java онлайн " + "проектов и стажировок."));
        experience_description.addOrganization(organization);
        organization = new Organization("Wrike", "https://www.wrike" + ".com/");
        organization.addPeriod(new Period(2014, Month.OCTOBER, 2016, Month.JANUARY, "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами " + "Wrike (Java 8 API, Maven, " + "Spring, " + "MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " + "аутентификация, " + "авторизация по OAuth1, OAuth2, JWT SSO."));
        experience_description.addOrganization(organization);
        organization = new Organization("RIT Center");
        organization.addPeriod(new Period(2012, Month.APRIL, 2014, Month.OCTOBER, "Java " + "архитектор",
                "Организация процесса разработки " + "системы ERP для разных окружений: релизная" + " политика, " +
                        "версионирование, ведение CI (Jenkins), миграция" + " базы (кастомизация Flyway), " +
                        "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД" + " и серверной " + "части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), " + "сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для " + "online " + "редактирование из браузера документов MS Office. Maven + plugin development, " + "Ant, Apache Commons, " + "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita," + " Python scripting, Unix shell " + "remote scripting via ssh tunnels, PL/Python"));
        experience_description.addOrganization(organization);
        organization = new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/");
        organization.addPeriod(new Period(2010, Month.DECEMBER, 2012, Month.APRIL, "Ведущий " + "программист",
                "Участие в проекте Deutsche " + "Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, " + "Jasper, " + " Oracle). Реализация " + "клиентской и серверной части CRM. Реализация " + "RIA-приложения для " + "администрирования, мониторинга " + "и анализа результатов в области " + "алгоритмического трейдинга. " + "JPA, Spring, Spring-MVC, GWT, " + "ExtGWT (GXT), Highstock," + " Commet, HTML5."));
        experience_description.addOrganization(organization);
        organization = new Organization("Yota", "https://www.yota.ru/");
        organization.addPeriod(new Period(2008, Month.JUNE, 2010, Month.DECEMBER, "Ведущий специалист", "Дизайн и " +
                "имплементация Java " + "EE" + "фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1," + " v3," +
                " OC4J, EJB3, JAX-WS RI 2.1, " + "Servlet" + "2.4, JSP, JMX, JMS, Maven2). Реализация " +
                "администрирования, статистики и мониторинга " + "фреймворка. " + "Разработка online JMX " + "клиента" +
                " (Python/ Jython, Django, ExtJS)"));
        experience_description.addOrganization(organization);
        organization = new Organization("Enkata", "http://enkata.com/");
        organization.addPeriod(new Period(2007, Month.MARCH, 2008, Month.JUNE, "Разработчик ПО", "Реализация " +
                "клиентской (Eclipse " + "RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей " +
                "кластерного J2EE приложения " + "(OLAP, Data mining)."));
        experience_description.addOrganization(organization);
        organization = new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html");
        organization.addPeriod(new Period(2005, Month.JANUARY, 2007, Month.FEBRUARY, "Разработчик ПО", "Разработка " +
                "информационной " + "модели, " + "проектирование интерфейсов, реализация и отладка ПО на мобильной " + "IN платформе Siemens " + "@vantage " + "(Java, Unix)."));
        experience_description.addOrganization(organization);
        organization = new Organization("Alcatel", "http://www.alcatel.ru/");
        organization.addPeriod(new Period(1997, Month.SEPTEMBER, 2005, Month.JANUARY, "Инженер " + "по " +
                "аппаратному и программному " + "тестированию", "Тестирование, отладка, внедрение ПО цифровой " +
                "телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        experience_description.addOrganization(organization);

        SectionType education = SectionType.EDUCATION;
        OrganizationSection education_description = new OrganizationSection();
        organization = new Organization("Coursera", "https://www.coursera.org/course/progfun");
        organization.addPeriod(new Period(2013, Month.MARCH, 2013, Month.MAY, "'Functional Programming Principles in "
                + "Scala' by Martin Odersky", null));
        education_description.addOrganization(organization);
        organization = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course" + ".html?ID"
                + "=22366");
        organization.addPeriod(new Period(2011, Month.MARCH, 2011, Month.APRIL, "Курс " + "Объектно-ориентированный "
                + "анализ ИС. " + "Концептуальное моделирование на UML.", null));
        education_description.addOrganization(organization);
        organization = new Organization("Siemens AG", "http://www.siemens.ru/");
        organization.addPeriod(new Period(2005, Month.JANUARY, 2005, Month.APRIL, "Курс " +
                "'Объектно-ориентированный " +
                "анализ ИС. " +
                "Концептуальное" + "моделирование на UML.'", null));
        education_description.addOrganization(organization);
        organization = new Organization("Alcatel", "http://www.alcatel.ru/");
        organization.addPeriod(new Period(1997, Month.SEPTEMBER, 1998, Month.MARCH, "6 месяцев " +
                "обучения " +
                "цифровым " +
                "телефонным сетям" +
                " " +
                "(Москва)", null));
        education_description.addOrganization(organization);
        organization = new Organization("Санкт-Петербургский национальный исследовательский университет " +
                "информационных технологий, механики и оптики", "http://www.ifmo.ru/");
        organization.addPeriod(new Period(1993, Month.OCTOBER, 1996, Month.JULY,
                "Аспирантура (программист С, С++)", null));
        organization.addPeriod(new Period(LocalDate.of(1987, Month.SEPTEMBER, 1), LocalDate.of(1993, Month.JULY, 1),
                "Инженер (программист Fortran, C)", null));
        education_description.addOrganization(organization);
        organization = new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/");
        organization.addPeriod(new Period(1984, Month.SEPTEMBER, 1987, Month.JUNE, "Закончил с " +
                "отличием", null));
        education_description.addOrganization(organization);


        resume.addSection(objective, objective_description);
        resume.addSection(personal, personal_description);
        resume.addSection(achievement, achievement_description);
        resume.addSection(qualifications, qualifications_description);
        resume.addSection(experience, experience_description);
        resume.addSection(education, education_description);


        for (ContactType type : ContactType.values()) {
            System.out.println(type + resume.getContacts(type));
        }
        System.out.println(" ");
        for (SectionType type : SectionType.values()) {
            System.out.println(type + "\n" + resume.getSections().get(type));
        }
        return resume;
    }
}

