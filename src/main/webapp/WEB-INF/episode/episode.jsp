

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Episode bearbeiten
            </c:when>
            <c:otherwise>
                Episode anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">     
        <div class="menuitem">
            <a href="<c:url value="/app/start/"/>">Start</a>
        </div>
        <div class="menuitem">
            <a href="<c:url value="/app/episode/"/>">Neue Episode</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/episode/list/"/>">Episodenliste</a>
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
                <label for="episode_user">Ersteller:</label>
                <div class="side-by-side">
                    <input type="text" name="episode_user" value="${user.username}" readonly="readonly">
                </div>
                
                <label for="episode_series">Serienname:</label>
                <div class="side-by-side">
                    <input type="text" name="episode_series" value="" >
                </div>
                
                <label for="episode_season">Staffel:</label>
                <div class="side-by-side">
                    <input type="text" name="episode_season" value="" >
                </div>
                
                <label for="episode_name">Episodenname:</label>
                <div class="side-by-side">
                    <input type="text" name="episode_name" value="" >
                </div>
                
                <label for="episode_number">Episodennummer:</label>
                <div class="side-by-side">
                    <input type="number" name="episode_number" value="" >
                </div>
                
                <label for="episode_description">Beschreibung:</label>
                <div class="side-by-side">
                    <textarea name="episode_description" value="" ></textarea>
                </div>
                
                  <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>
                  </div>
        
            </div>
                
                <%-- Fehlermeldungen --%>
            <c:if test="${!empty episode_form.errors}">
                <ul class="errors">
                    <c:forEach items="${episode_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
            
        </form>
    </jsp:attribute>
</template:base>