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
import dhbwka.wwi.vertsys.javaee.mediavote.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb.EpisodeBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import dhbwka.wwi.vertsys.javaee.mediavote.score.ejb.ScoreBean;
import java.io.IOException;
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
@WebServlet(urlPatterns = {"/app/episode/"})
public class EpisodeServlet extends HttpServlet {

    @EJB
    private ScoreBean scoreBean;
    
    @EJB
    private EpisodeBean episodeBean;
    
    @EJB
    private UserBean userBean;
    

   @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        User user = this.userBean.getCurrentUser();
        request.setAttribute("user", user);
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/episode/episode.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        session.removeAttribute("episode_form");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = this.userBean.getCurrentUser();
        
        // Formulareingaben auslesen    
        String name = request.getParameter("episode_name");
        String series = request.getParameter("episode_series");
        String season = request.getParameter("episode_season");
        int number = Integer.parseInt(request.getParameter("episode_number"));
        String description = request.getParameter("episode_description");
        
        Episode episode = new Episode(user, name, series, season, number, description);

        episodeBean.saveNew(episode);
   
   
        // Keine Fehler: Startseite aufrufen
        response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));      
        
    }
}
