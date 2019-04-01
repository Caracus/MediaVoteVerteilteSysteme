<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Aufgaben
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/task/new/"/>">Aufgabe anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/categories/"/>">Kategorien bearbeiten</a>
        </div>
        
        
        
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty episodes}">
                <p>
                    Es wurden keine Aufgaben gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.mediavote.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Ersteller</th>
                            <th>Serienname</th>
                            <th>Staffel</th>
                            <th>Episodenname</th>
                            <th>Episodennummer</th>
                        </tr>
                    </thead>
                    <c:forEach items="${episodes}" var="episode">
                        <tr>
                            <td>
                                <c:out value=""/>
                            </td>
                            <td>
                                <c:out value=""/>
                            </td>
                            <td>
                                <c:out value=""/>
                            </td>
                            <td>
                                <c:out value=""/>
                            </td>
                            <td>
                                <c:out value=""/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>
