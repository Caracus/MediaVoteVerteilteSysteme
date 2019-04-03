<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Episoden
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
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
        <%-- Suchfilter --%>

            
        <form method="GET" class="horizontal" id="search">
             <div id="container">
             <div class="buttonInside">
             <input type="text" name="search_text" value="${param.search_text}" id="searchInput" placeholder="Serienname">
             <button class="icon-search" type="submit" id="searchButton">Suchen</button>
            </div>
            </div>
        </form>
            

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty responseList}">
                <p>
                    Es wurden keine Episoden gefunden. 😪
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
                            <th>Deine Bewertung</th>
                            <th>Durch. Bewertung</th>
                        </tr>
                    </thead>
                    <c:forEach items="${responseList}" var="responseListItem">
                        <tr>
                            <td>
                                <c:out value="${responseListItem.episode.user.username}"/>
                            </td>
                            <td>
                                <c:out value="${responseListItem.episode.series}"/>
                            </td>
                            <td>
                                <c:out value="${responseListItem.episode.season}"/>
                            </td>
                            <td>
                                <c:out value="${responseListItem.episode.name}"/>
                            </td>
                            <td>
                                <c:out value="${responseListItem.episode.number}"/>
                            </td>
                            <td>
                                 <a href="<c:url value="/app/score/${responseListItem.episode.id}"/>">
                                <c:out value="${responseListItem.score}"/>
                                 </a>
                            </td>
                            <td>
                                <c:out value="${responseListItem.episode.avgRating}"/>
                            </td>
                                                          
                               
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>
