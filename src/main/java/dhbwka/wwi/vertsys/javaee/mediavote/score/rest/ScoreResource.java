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
import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.web.EpisodeListServlet;
import dhbwka.wwi.vertsys.javaee.mediavote.score.ejb.ScoreBean;
import dhbwka.wwi.vertsys.javaee.mediavote.score.jpa.Score;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private static final Logger logger = Logger.getLogger(EpisodeListServlet.class.getName());

    @EJB
    ScoreBean scoreBean;
    
    @EJB
    UserBean userBean;

    @GET
    public String findUserScores(){
        //Gibt die Top 10 der Bewertungen des anfragenden Nutzers zurück
        User user = userBean.getCurrentUser();
        List<Score> scores = this.scoreBean.findByUser(user.getUsername()); 
        
        int maxLengthList = 10;
        if(scores.size() < 10){
          maxLengthList = scores.size();  
        }
        List<Score> topScores = new ArrayList();
        for(int i=0; i < maxLengthList; i++){
            Score score = scores.get(i);
            score.setOperator(null);
            score.getEpisode().setUser(null);
            score.getEpisode().setName(score.getEpisode().getName().replace(" ","_"));
            score.getEpisode().setSeries(score.getEpisode().getSeries().replace(" ","_"));
            topScores.add(score);
        }
        
        String json = gson.toJson(topScores);
        return json;
    }

   
}