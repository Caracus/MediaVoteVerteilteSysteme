<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Start
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
        
        <div class="menuitem">
            <a href="<c:url value="/app/rest"/>">REST Aufruf</a>
        </div>
        
    </jsp:attribute>

    <jsp:attribute name="content">

        <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.mediavote.common.web.WebUtils"/>
        
        <h1>Willkommen ${firstname}</h1>


    </jsp:attribute>
        
</template:base>
