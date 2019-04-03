/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.score.rest;

import com.google.gson.Gson;
import dhbwka.wwi.vertsys.javaee.mediavote.score.ejb.ScoreBean;
import dhbwka.wwi.vertsys.javaee.mediavote.score.jpa.Score;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("scores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ScoreResource {
    
    Gson gson = new Gson();

    @EJB
    ScoreBean scoreBean;

    @GET
    public String findEpisodes(){
        List<Score> scores = this.scoreBean.findByUserName(); 
        List<Score> top10Scores = new ArrayList();
        for(int i=0;i<10;i++){
            top10Scores.add(scores.get(i));
        }
        String json = gson.toJson(top10Scores);
        return json;
    }

   
}