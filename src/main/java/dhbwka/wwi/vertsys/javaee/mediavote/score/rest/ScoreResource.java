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
    public String findScores(){
        //User user = userBean.getCurrentUser();
        logger.log(Level.INFO, "-----------------------------------Stufe0");
        //logger.log(Level.INFO, "Name "+user.getUsername());
        logger.log(Level.INFO, "-----------------------------------Stufe1");
        //List<Score> scores = this.scoreBean.findTop(); 
        List<Score> scores = this.scoreBean.findByUser("hoppy510"); 
        //logger.log(Level.INFO, "Name "+user.getUsername());
        //List<Score> scores = this.scoreBean.findByUserAndEpisode("test123", 1L); 
        //List<Score> scores = this.scoreBean.findAll(); 
        
        int maxLengthList = 10;
        int scoreListLength = scores.size();
        logger.log(Level.INFO, "-----------------------------------Stufe2");
        if(scoreListLength < 10){
          maxLengthList = scores.size();  
        }
        logger.log(Level.INFO, "-----------------------------------Stufe3");
        List<Score> top10Scores = new ArrayList();
        for(int i=0;i<maxLengthList;i++){
            top10Scores.add(scores.get(i));
            logger.log(Level.INFO, "-----------------------------------Stufe4");
        }
        
        String json = gson.toJson(top10Scores);
        logger.log(Level.INFO, "--------------------------------------Stufe5");
        return json;
    }

   
}