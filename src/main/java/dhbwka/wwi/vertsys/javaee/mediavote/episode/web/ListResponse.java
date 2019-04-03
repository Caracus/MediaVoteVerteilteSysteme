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

import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;

/**
 * Hilfsklasse als Antwortobjekt
 */

public class ListResponse {

   private Episode episode;
   private String score;

    public Episode getEpisode() {
        return episode;
    }

    public ListResponse() {
    }
    
    

    public ListResponse(Episode episode, String score) {
        this.episode = episode;
        this.score = score;
    }
    
    
    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ListResponse{" + "episode=" + episode + ", score=" + score + '}';
    }

    
    
    
}
