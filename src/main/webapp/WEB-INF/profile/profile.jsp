<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
           Profil bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/tasks/list/"/>">Liste</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/profile/"/>">Mein Profil</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="profile_firstname">Vorname</label>
                <div class="side-by-side">
                    <input type="text" name="profile_firstname" value="${firstname}">
                </div>
                
                <label for="profile_lastname">Nachname</label>
                <div class="side-by-side">
                    <input type="text" name="profile_lastname" value="${lastname}">
                </div>
                
                <label for="profile_password1">
                    Passwort:
                </label>
                <div class="side-by-side">
                    <input type="password" name="profile_password1" value="${profile_form.values["profile_password1"][0]}">
                </div>

                <label for="profile_password2">
                    Passwort (wdh.):
                </label>
                <div class="side-by-side">
                    <input type="password" name="profile_password2" value="${profile_form.values["profile_password2"][0]}">
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Daten aktualisieren
                    </button>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty profile_form.errors}">
                <ul class="errors">
                    <c:forEach items="${profile_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>