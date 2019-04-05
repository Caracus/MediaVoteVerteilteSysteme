<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Episoden
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/style.css"/>" />
    </jsp:attribute>


    <jsp:attribute name="content">


        <h1>Startseite</h1>
        <div id="navigation">
            <button id="allEpisodesButton" class="menuButton" >Alle Episoden</button>
            <button id="top10Button" class="menuButton" >Top 10</button>
        </div>

        <form id="logInForm">
            <div id="loginDiv">
                <input type="text" id="usernameField" value="" placeholder="Username">
                <input type="text" id="usernamePassword" value="" placeholder="Passwort">
            </div>
        </form>

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
              console.log(tempData);
              console.log("PointA");
              //var tempData2 = JSON.parse(tempData);
              //console.log(tempData2);
              consoleLogger(tempData);
              
              console.log("PointB");

                //console.log(currentUser.findEpisodes());

                //createTableForEpisodes(currentUser.findEpisodes());
            }

            function getTop10Episodes() {
                let username = document.getElementById("usernameField").value;
                let password = document.getElementById("usernamePassword").value;
                console.log(username);
                console.log(password);

                let currentUser = new EpisodeClient();
                currentUser.setAuthData(username, password);

                //  createTableForTopEpisodes(currentUser.findTop());
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
                    //console.log("Point1");
                    //console.log(response.json());
                    //console.log("Point2");
                    return await response.json();
                }

                async findTop() {
                    let response = await fetch(this.url, {
                        headers: {
                            "accept": "application/json",
                            //"content-type": "application/json", führt zu rejected statt pending beim promise
                            "authorization": "Basic " + btoa(this.username + ":" + this.password)
                        }
                    });
                    console.log(response);
                    //console.log(JSON.parse(response.json()));
                    return await response.json();
                }
            }





            function createTableForEpisodes(data) {

                let episodes = JSON.parse(data);

                episodes.forEach(episode => {

                    let table = document.getElementById("tabelle");
                    let tr = table.insertRow(1);
                    let td1 = document.createElement("td");
                    let td2 = document.createElement("td");
                    let td3 = document.createElement("td");
                    let td4 = document.createElement("td");
                    let td5 = document.createElement("td");
                    td1.innerHTML = '<input type="text"  value=' + episode.series + '>';
                    td2.innerHTML = '<input type="text"  value=' + episode.season + '>';
                    td3.innerHTML = '<input type="text"  value=' + episode.name + '>';
                    td4.innerHTML = '<input type="text"  value=' + episode.number + '>';
                    td5.innerHTML = '<input type="text"  value=' + episode.avgRating + '>';
                    tr.appendChild(td1);
                    tr.appendChild(td2);
                    tr.appendChild(td3);
                    tr.appendChild(td4);
                    tr.appendChild(td5);

                    console.log(episode.name);
                });
            }

            function consoleLogger(data) {
                data.then(function(value){
                    console.log(value[0]);
                    console.log(value[0].name);
                    
                    console.log(value.length);
                    
                    for(let i=0;i<value.length;i++){
                        console.log(value[i].name);
                    }
                    
                });
                
                
                console.log(data);
            }
            
                function createTableForTopEpisodes(data) {

                let episodes = JSON.parse(data);

                episodes.forEach(episode => {

                    let table = document.getElementById("tabelle");
                    let tr = table.insertRow(1);
                    let td1 = document.createElement("td");
                    let td2 = document.createElement("td");
                    let td3 = document.createElement("td");
                    let td4 = document.createElement("td");
                    let td5 = document.createElement("td");
                    td1.innerHTML = '<input type="text"  value=' + episode.series + '>';
                    td2.innerHTML = '<input type="text"  value=' + episode.season + '>';
                    td3.innerHTML = '<input type="text"  value=' + episode.name + '>';
                    td4.innerHTML = '<input type="text"  value=' + episode.number + '>';
                    td5.innerHTML = '<input type="text"  value=' + episode.avgRating + '>';
                    tr.appendChild(td1);
                    tr.appendChild(td2);
                    tr.appendChild(td3);
                    tr.appendChild(td4);
                    tr.appendChild(td5);

                    console.log(episode.name);
                });
            }

            window.addEventListener("load", () => {
                document.getElementById("allEpisodesButton").addEventListener("click", getAllEpisodes);
                document.getElementById("top10Button").addEventListener("click", getTop10Episodes);
            });
        </script>


    </jsp:attribute>
</template:base>
