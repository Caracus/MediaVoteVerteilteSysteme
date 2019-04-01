<%-- 
    Document   : episode
    Created on : 01.04.2019, 09:54:57
    Author     : shans
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Aufgabe bearbeiten
            </c:when>
            <c:otherwise>
                Aufgabe anlegen
            </c:otherwise>
        </c:choose>
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
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="episode_user">Ersteller:</label>
                <div class="side-by-side">
                    <input type="text" name="episode_user" value="" readonly="readonly">
                </div>
                
                <label for="episode_series">Serienname:</label>
                <div class="side-by-side">
                    <input type="text" name="episode_series" value="" readonly="readonly">
                </div>
                
                <label for="episode_season">Staffel:</label>
                <div class="side-by-side">
                    <input type="text" name="episode_series" value="" readonly="readonly">
                </div>
                
                <label for="episode_name">Episodenname:</label>
                <div class="side-by-side">
                    <input type="text" name="episode_name" value="" readonly="readonly">
                </div>
                
                <label for="episode_number">Episodennummer:</label>
                <div class="side-by-side">
                    <input type="number" name="episode_number" value="" readonly="readonly">
                </div>
                
                <label for="episode_description">Beschreibung:</label>
                <div class="side-by-side">
                    <textarea name="episode_description" value="" readonly="readonly"></textarea>
                </div>
                
                  <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>
                  </div>
        
            </div>
        </form>
    </jsp:attribute>
</template:base>