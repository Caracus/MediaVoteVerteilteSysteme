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
import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mediavote.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.mediavote.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb.EpisodeBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import dhbwka.wwi.vertsys.javaee.mediavote.score.ejb.ScoreBean;
import dhbwka.wwi.vertsys.javaee.mediavote.score.jpa.Score;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet für die tabellarische Auflisten der Aufgaben.
 */
@WebServlet(urlPatterns = {"/app/episode/*"})
public class EpisodeServlet extends HttpServlet {

    @EJB
    private ScoreBean scoreBean;
    
    @EJB
    private EpisodeBean episodeBean;
    
    @EJB
    private UserBean userBean;
    
    @EJB
    private ValidationBean validationBean;
    

   @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        User user = this.userBean.getCurrentUser();
        request.setAttribute("user", user);
           
        try {
            Episode episode = getRequestedEpisode(request);
            request.setAttribute("episode", episode);
        } catch(Exception e) {
            
        }
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/episode/episode.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        session.removeAttribute("episode_form");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<String> errors = new ArrayList<>();
        User user = this.userBean.getCurrentUser();
        
        // Formulareingaben auslesen    
        String name = request.getParameter("episode_name");
        String series = request.getParameter("episode_series");
        String season = request.getParameter("episode_season");
        int number = Integer.parseInt(request.getParameter("episode_number"));
        String description = request.getParameter("episode_description");
        String idString = request.getParameter("episode_id");
        
        Episode episode;
        Boolean newEntry = false;
        
        if(idString == null || idString.isEmpty()) {
            episode = new Episode(user, name, series, season, number, description);
            newEntry = true;
        } else {
            Long id = Long.parseLong(idString);
            episode = new Episode(id, user, name, series, season, number, description);
        }
        
    
       this.validationBean.validate(episode, errors);

        if (errors.isEmpty()) {
            if(newEntry) {
                episodeBean.saveNew(episode);
            }
            else {
                episodeBean.update(episode);
            }
            

            response.sendRedirect(WebUtils.appUrl(request, "/app/episode/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("episode_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }    
        
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
