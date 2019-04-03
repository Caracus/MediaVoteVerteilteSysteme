<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title"> 
                Bewertung abgeben
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
                <label for="episode_user">Angemeldet als</label>
                <div class="side-by-side">
                    <input type="text" name="episode_user" value="${user.username}" readonly="readonly">
                </div>
                
                <label for="episode_name">Episodenname</label>
                <div class="side-by-side">
                    <input type="text" name="episode_name" value="${episode.name}" readonly="readonly" >
                </div>
                
                <label for="episode_score">Deine Bewertung von 1 bis 10:</label>
                <div class="side-by-side">
                    <input type="number" name="episode_score" min="1" max="10" value="${score.rating}" >
                </div>
                    
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>
                </div>
        
            </div>
                
            <%-- Fehlermeldungen --%>
            <c:if test="${!empty score_form.errors}">
                <ul class="errors">
                    <c:forEach items="${score_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>
