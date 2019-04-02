/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.score.web;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mediavote.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb.EpisodeBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import dhbwka.wwi.vertsys.javaee.mediavote.score.ejb.ScoreBean;
import dhbwka.wwi.vertsys.javaee.mediavote.score.jpa.Score;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Seite zum Anlegen oder Bearbeiten einer Aufgabe.
 */
@WebServlet(urlPatterns = "/app/score/*")
public class ScoreServlet extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(ScoreServlet.class.getName());

    @EJB
    private ScoreBean scoreBean;
    
    @EJB
    private EpisodeBean episodeBean;
    
    @EJB
    private UserBean userBean;
    

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = this.userBean.getCurrentUser();
        List<Score> scores;
        try {
            Episode episode = getRequestedEpisode(request);
            scores = scoreBean.findByUserAndEpisode(user.getUsername(), episode.getId());
            request.setAttribute("episode", episode);
            if (!scores.isEmpty()) {
            //Bewertung abgegeben, muss ein Element enhalten
            Score score = scores.get(0);
            request.setAttribute("score", score);
            }
        } catch(Exception e) {
            
        }
        
        //Werte setzen
        request.setAttribute("user", user);
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/episode/score.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = this.userBean.getCurrentUser();
        int rating = Integer.parseInt(request.getParameter("episode_score"));
        Episode episode = getRequestedEpisode(request);    
        
        //Episode episode = new Episode(user, name, series, season, number, description);
        
        List<Score> scores = scoreBean.findByUserAndEpisode(user.getUsername(), episode.getId());
        logger.log(Level.INFO, "Alle Scores zur Episode");
        for(Score score : scores) {
            logger.log(Level.INFO, score.toString());
        }
        
        
        
        if (!scores.isEmpty()) {
           Score score = scores.get(0);
           score.setRating(rating);
           scoreBean.update(score); 
           //episode.calculateAvgRating();
           //episodeBean.update(episode);
        }
        else {
            Score score = new Score(user, episode, rating);
            scoreBean.saveNew(score);
            
            //episode.addScore(score);
            //episode.calculateAvgRating();
            //episodeBean.update(episode);
        }
        
        
     // Keine Fehler: Startseite aufrufen
        response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));   
        
    }
    
    private Episode getRequestedEpisode(HttpServletRequest request) {
         // ID aus der URL herausschneiden
        String episodeIdString = request.getPathInfo();

        if (episodeIdString == null) {
            episodeIdString = "/1";
        }

        episodeIdString = episodeIdString.substring(1);

        if (episodeIdString.endsWith("/")) {
            episodeIdString = episodeIdString.substring(0, episodeIdString.length() - 1);
        }
        
        long episodeId = Long.parseLong(episodeIdString);
        Episode episode;
    
        try {
           episode = this.episodeBean.findById(episodeId); 
        } catch(NumberFormatException e) {
            throw e;
        }       
        return episode;
    }


}
