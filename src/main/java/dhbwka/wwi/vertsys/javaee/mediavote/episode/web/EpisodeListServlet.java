/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.episode.web;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb.EpisodeBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import dhbwka.wwi.vertsys.javaee.mediavote.score.ejb.ScoreBean;
import dhbwka.wwi.vertsys.javaee.mediavote.score.jpa.Score;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet für die tabellarische Auflisten der Aufgaben.
 */
@WebServlet(urlPatterns = {"/app/episode/list/"})
public class EpisodeListServlet extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(EpisodeListServlet.class.getName());

    @EJB
    private ScoreBean scoreBean;
    
    @EJB
    private EpisodeBean episodeBean;
    
    @EJB
    private UserBean userBean;
    

   @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = userBean.getCurrentUser();
        
        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
                 
        List<Episode> episodes;
        
        if(searchText == null || searchText.isEmpty()) {
            episodes = this.episodeBean.findAll();
        } else {
            episodes = this.episodeBean.findBySeries(searchText);
        }
                
        
        List<ListResponse> responseList = new ArrayList<>();
        for(Episode ep : episodes) {
            
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
            
            //Ermittle Nutzer-Bewertung
                     
            scores = scoreBean.findByUserAndEpisode(user.getUsername(), ep.getId());   
            String score;
             
            if (!scores.isEmpty()) {
               score = "" + scores.get(0).getRating();

            }
            else {
                score = "Jetzt bewerten";
            }
            ListResponse listResponse = new ListResponse(ep, score);
            responseList.add(listResponse);
        
        }
        request.setAttribute("responseList", responseList);
              
                
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/episode/list.jsp");
        dispatcher.forward(request, response);
        


    }

}
