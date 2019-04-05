    document.getElementById("allEpisodesButton").addEventListener("click", getLoginData());
    document.getElementById("top10Button").addEventListener("click", getLoginData());
    
    
    
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
        td1.innerHTML = '<input type="text"  value='+episode.series+'>';
        td2.innerHTML = '<input type="text"  value='+episode.season+'>';
        td3.innerHTML = '<input type="text"  value='+episode.name+'>';
        td4.innerHTML = '<input type="text"  value='+episode.number+'>';
        td5.innerHTML = '<input type="text"  value='+episode.avgRating+'>';
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);

        console.log(episode.name);
    });
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
        td1.innerHTML = '<input type="text"  value='+episode.series+'>';
        td2.innerHTML = '<input type="text"  value='+episode.season+'>';
        td3.innerHTML = '<input type="text"  value='+episode.name+'>';
        td4.innerHTML = '<input type="text"  value='+episode.number+'>';
        td5.innerHTML = '<input type="text"  value='+episode.avgRating+'>';
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);

        console.log(episode.name);
    });
}

  function getLoginData() {
        let username = document.getElementById("usernameField").value;
        let password = document.getElementById("usernamePassword").value;
        console.log(username);
        console.log(password);

        let currentUser = new EpisodeClient();
        currentUser.setAuthData(username, password);

        createTableForEpisodes(currentUser.findEpisodes());
    }

