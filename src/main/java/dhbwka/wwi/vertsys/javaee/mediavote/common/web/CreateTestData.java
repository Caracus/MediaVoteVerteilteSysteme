/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.common.web;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb.EpisodeBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import dhbwka.wwi.vertsys.javaee.mediavote.score.ejb.ScoreBean;
import dhbwka.wwi.vertsys.javaee.mediavote.score.jpa.Score;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Testdatenerzeugung
 * Darf nur einmal aufgerufen werden
 */
@WebServlet(urlPatterns = {"/app/testdata"})
public class CreateTestData extends HttpServlet {
    
    @EJB
    UserBean userBean;
    
    @EJB
    EpisodeBean episodeBean;
    
    @EJB 
    ScoreBean scoreBean;
    
    /**
     * GET-Anfrage: Seite anzeigen
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        
        
        try {
            userBean.signup("testUser1", "test123", "Peter", "Müller");
        } catch(UserBean.UserAlreadyExistsException e) {
        }
   
         try {
            userBean.signup("testUser2", "test123", "Moritz", "Meier");
        } catch(UserBean.UserAlreadyExistsException e) {
        }
                
        try {
            userBean.signup("testUser3", "test123", "Bob", "Baumeister");
        } catch(UserBean.UserAlreadyExistsException e) {
        }
                        
        try {
            userBean.signup("testUser4", "test123", "Max", "Mustermann");
        } catch(UserBean.UserAlreadyExistsException e) {
        }
        
        
        User user1 = userBean.findByUsername("testUser1");
        User user2 = userBean.findByUsername("testUser2");
        User user3 = userBean.findByUsername("testUser3");
        User user4 = userBean.findByUsername("testUser4");
        
           
            Episode episode1 = new Episode(user1, "Leuchtfeuer", "Star Trek: Discovery", "1", 1, "Die neuen Mitglieder des „Star-Trek“-Universums erforschen neue Welten und Zivilisationen.");
            Episode episode2 = new Episode(user1, "Die Rückkehr", "Arrow", "1", 1, "Nach einem Schiffsuntergang wird der Milliardär und Playboy Oliver Queen (Stephen Amell) fünf Jahre lang für tot erklärt, bevor er auf einer verlassenen Insel mitten im Pazifik gefunden wird.");
            Episode episode3 = new Episode(user1, "Aus großer Kraft folgt...", "Agents of Shield", "1", 1, "Pilot");
            Episode episode4 = new Episode(user2, "50.000 Kilometer", "The Expanse", "1", 1, "James Holden (Steven Strait) und seine Crew befassen sich mit einem wichtigen Notfall, während Detective Miller (Thomas Jane) auf der Suche nach einer vermissten Person ist. ");
            Episode episode5 = new Episode(user2, "Der Winter naht", "Game of Thrones", "1", 1, "Abgetrennte Köpfe, barbusige Frauen und mittelalterliche Action: Der Winter kommt zurück mit dem Auftakt der neuen Serie Game of Thrones.");
            Episode episode6 = new Episode(user3, "Der Engelskelch", "Shadowhunters", "1", 1, "An ihrem 18. Geburtstag entdeckt eine Frau ihre Herkunft aus einer langen Ahnenreihe von Shadowhunters - menschliche Engel, die Dämonen jagen. ");
            Episode episode7 = new Episode(user4, "Das Urteil", "Star Trek: Discovery", "1", 2, "Inmitten des Chaos blickt Burnham auf ihr Aufwachsen unter Vulkaniern zurück, um daraus eine Lehre zu ziehen. ");
            Episode episode8 = new Episode(user4, "Lakaien und Könige", "Star Trek: Discovery", "1", 3, "Burnham (Sonequa Martin-Green) findet sich an Bord der U.S.S. Discovery wieder, wo sie schnell realisiert, dass nicht alles so ist, wie es scheint, inklusive des mysteriösen Kapitäns Gabriel Lorca (Jason Isaacs).  ");
            
            episodeBean.saveNew(episode1);
            episodeBean.saveNew(episode2);
            episodeBean.saveNew(episode3);
            episodeBean.saveNew(episode4);
            episodeBean.saveNew(episode5);
            episodeBean.saveNew(episode6);
            episodeBean.saveNew(episode7);
            episodeBean.saveNew(episode8);
            
            Score score1 = new Score(user1, episode1, 9);
            Score score2 = new Score(user2, episode1, 8);
            Score score3 = new Score(user4, episode7, 7);
            Score score4 = new Score(user2, episode2, 5);
            Score score5 = new Score(user1, episode3, 6);
            Score score6 = new Score(user2, episode4, 4);
            Score score7 = new Score(user1, episode5, 8);
            Score score8 = new Score(user2, episode6, 5);
            Score score9 = new Score(user3, episode8, 7);
           
           scoreBean.saveNew(score1);
           scoreBean.saveNew(score2);
           scoreBean.saveNew(score3);
           scoreBean.saveNew(score4);
           scoreBean.saveNew(score5);
           scoreBean.saveNew(score6);
           scoreBean.saveNew(score7);
           scoreBean.saveNew(score8);
           scoreBean.saveNew(score9);
        
        
        response.sendRedirect(WebUtils.appUrl(request, "/app/start/"));
    }

}