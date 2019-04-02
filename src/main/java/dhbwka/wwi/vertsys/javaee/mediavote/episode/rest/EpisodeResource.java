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

    @GET
    public String findEpisodes() {
        List<Episode> episodes = this.episodeBean.findAll();
        //Entferne Userdaten aus zu übergebendem Datensatz
        episodes.forEach((ep) -> {
            ep.setUser(null);
        });
                 
        String json = gson.toJson(episodes);
        return json;
    } 
   
}