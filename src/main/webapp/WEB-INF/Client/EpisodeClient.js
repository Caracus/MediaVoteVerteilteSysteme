

"use strict";

class EpisodeClient {

    constructor(url) {
        this.url = url || "http://localhost:8080/MediaVote/api/episodes";
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
                //"content-type": "application/json", führt zu rejected statt pending beim promise
                "authorization": "Basic " + btoa(this.username + ":" + this.password)
            }
        });
        console.log(response);
        //console.log(JSON.parse(response.json()));
        return await response.json();
    }



}
