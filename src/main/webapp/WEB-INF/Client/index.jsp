<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<template:base>
   <jsp:attribute name="title">
      Rest Abfragen
   </jsp:attribute>
   <jsp:attribute name="head">
      <link rel="stylesheet" href="
      <c:url value="/css/style.css"/>
      " />
   </jsp:attribute>
   <jsp:attribute name="menu">
      <div class="menuitem">
         <a href="
         <c:url value="/app/start/"/>
         ">Start</a>
      </div>
   </jsp:attribute>
   <jsp:attribute name="content">
      <div class="wrapper">
         <label id="loginDiv">
         <input class="innerWrapper" type="text" id="usernameField" value="" placeholder="Benutzername">
         <input class="innerWrapper" type="password" id="usernamePassword" value="" placeholder="Passwort">
         </label>   
         <label id="navigation">
         <button id="allEpisodesButton" class="innerWrapper" >Alle Episoden</button>
         <button id="top10Button" class="innerWrapper" >Deine Top 10</button>
         </label>
      </div>
      <h1>Rest Episodenliste</h1>
      <br>
      <table id="tabelle">
         <tr>
            <th>Serienname</th>
            <th>Staffel</th>
            <th>Episodenname</th>
            <th>Episodennummer</th>
            <th>Bewertung</th>
         </tr>
      </table>
      <script>
         function getAllEpisodes() {
             let username = document.getElementById("usernameField").value;
             let password = document.getElementById("usernamePassword").value;
             console.log(username);
             console.log(password);
         
             let currentUser = new EpisodeClient();
             currentUser.setAuthData(username, password);
             
           var tempData = currentUser.findEpisodes();
           fcAllEpisodes(tempData);
         }
         
         function getTop10Episodes() {
             let username = document.getElementById("usernameField").value;
             let password = document.getElementById("usernamePassword").value;
             console.log(username);
             console.log(password);
         
             let currentUser = new EpisodeClientTop();
             currentUser.setAuthData(username, password);
             
             var tempData = currentUser.findTop();
             fcTopEpisodes(tempData);
         }
         
         class EpisodeClient {
         
             constructor(url) {
               
                 this.url = url || "../api/episodes";
                 console.log(this.url);
                 this.username = "";
                 this.password = "";
             }
         
             setAuthData(username, password) {
                 this.username = username;
                 this.password = password;
             }
         
             async findEpisodes() {
                 let response = await fetch(this.url, {
                     headers: {
                         "accept": "application/json",
                         "content-type": "application/json", 
                         "authorization": "Basic " + btoa(this.username + ":" + this.password)
                     }
                 });
                 try {
                    return await response.json();
                }
                catch(e) {
                 window.alert("Bitte gültige Anmeldedaten eintragen.");
                }
             }
         }
         
         class EpisodeClientTop {
         
             constructor(url) {
               
                 this.url = url || "../api/scores";
                 console.log(this.url);
                 this.username = "";
                 this.password = "";
             }
         
             setAuthData(username, password) {
                 this.username = username;
                 this.password = password;
             }
         
            async findTop() {
                 console.log(this.url);
                 let response = await fetch(this.url, {
                     headers: {
                         "accept": "application/json",
                         "content-type": "application/json", 
                         "authorization": "Basic " + btoa(this.username + ":" + this.password)
                     }
                 });
                 try {
                    return await response.json();
                } 
                catch(e) {
                 window.alert("Bitte gültige Anmeldedaten eintragen.");
                 }
             }
             }
         
         function fcAllEpisodes(data) {
             data.then(function(value){
                 
                 for(var i= 0;document.getElementById("tabelle").rows.length-1;i++){
                 document.getElementById("tabelle").deleteRow(1);
                 }
                 
                 for(let i=0;i<value.length;i++){
                  let table = document.getElementById("tabelle");
                 let tr = table.insertRow(1+i);
                 let td1 = document.createElement("td");
                 let td2 = document.createElement("td");
                 let td3 = document.createElement("td");
                 let td4 = document.createElement("td");
                 let td5 = document.createElement("td");
                 td1.innerHTML = '<input type="text"  value=' + value[i].series + '>';
                 td2.innerHTML = '<input type="text"  value=' + value[i].season + '>';
                 td3.innerHTML = '<input type="text"  value=' + value[i].name + '>';
                 td4.innerHTML = '<input type="text"  value=' + value[i].number + '>';
                 td5.innerHTML = '<input type="text"  value=' + value[i].avgRating + '>';
                 tr.appendChild(td1);
                 tr.appendChild(td2);
                 tr.appendChild(td3);
                 tr.appendChild(td4);
                 tr.appendChild(td5);
                 }                   
             });
         }
         
        function fcTopEpisodes(data) {
             data.then(function(value){
                 
                 for(var i= 0;document.getElementById("tabelle").rows.length-1;i++){
                 document.getElementById("tabelle").deleteRow(1);
                 }
                 
                 for(let i=0;i<value.length;i++){
                  let table = document.getElementById("tabelle");
                 let tr = table.insertRow(1+i);
                 let td1 = document.createElement("td");
                 let td2 = document.createElement("td");
                 let td3 = document.createElement("td");
                 let td4 = document.createElement("td");
                 let td5 = document.createElement("td");
                 td1.innerHTML = '<input type="text"  value=' + value[i].episode.series + '>';
                 td2.innerHTML = '<input type="text"  value=' + value[i].episode.season + '>';
                 td3.innerHTML = '<input type="text"  value=' + value[i].episode.name + '>';
                 td4.innerHTML = '<input type="text"  value=' + value[i].episode.number + '>';
                 td5.innerHTML = '<input type="text"  value=' + value[i].rating + '>';
                 tr.appendChild(td1);
                 tr.appendChild(td2);
                 tr.appendChild(td3);
                 tr.appendChild(td4);
                 tr.appendChild(td5);
                 }                   
             });
         }
         
            /**
            function replaceUnderscores(stringy) {
                return stringy.replace("_"," ");
             } 
             */
         
         window.addEventListener("load", () => {
             document.getElementById("allEpisodesButton").addEventListener("click", getAllEpisodes);
             document.getElementById("top10Button").addEventListener("click", getTop10Episodes);
         });
      </script>
   </jsp:attribute>
</template:base>
