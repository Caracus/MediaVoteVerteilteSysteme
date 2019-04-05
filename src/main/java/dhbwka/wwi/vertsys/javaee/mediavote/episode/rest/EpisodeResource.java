/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.episode.rest;

import com.google.gson.Gson;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb.EpisodeBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import dhbwka.wwi.vertsys.javaee.mediavote.score.ejb.ScoreBean;
import dhbwka.wwi.vertsys.javaee.mediavote.score.jpa.Score;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("episodes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EpisodeResource {
    
    Gson gson = new Gson();

    @EJB
    private EpisodeBean episodeBean;
    
    @EJB
    private ScoreBean scoreBean;

    @GET
    public String findEpisodes() {
        List<Episode> episodes = this.episodeBean.findAll();
          
        episodes.forEach((ep) -> {
            //Entferne Userdaten aus zu übergebendem Datensatz
            ep.setUser(null);
            
            //Berechne Durchschnittswert
            List<Score> scores = scoreBean.findByEpisode(ep.getId());
            int i = 0;
            double scoreSum = 0;
            for(Score score : scores) {
                scoreSum = scoreSum + score.getRating();
                i++;
            }
            double avgScore = 0.0;
            if(i > 0) {
                avgScore = scoreSum / i;
            }
            ep.setAvgRating(avgScore);
            
        });     
        //Collections.sort(episodes); Ruiniert die Liste
       String json = gson.toJson(episodes);
       return json;
    } 
   
}