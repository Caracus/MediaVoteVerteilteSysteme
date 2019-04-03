/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.score.ejb;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.mediavote.score.jpa.Score;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Bean für Scores
 */
@Stateless
@RolesAllowed("app-user")
public class ScoreBean extends EntityBean<Score, Long> { 

    public ScoreBean() {
        super(Score.class);
    }
    
    public List<Score> findByUserAndEpisode(String username, Long episodeId) {
        return em.createQuery("SELECT s FROM Score s WHERE s.operator.username = :username AND s.episode.id = :episodeId")
                .setParameter("username", username)
                .setParameter("episodeId", episodeId)
                .getResultList();
    }
    
    public List<Score> findByEpisode(Long episodeId) {
        return em.createQuery("SELECT s FROM Score s WHERE s.episode.id = :episodeId")
                .setParameter("episodeId", episodeId)
                .getResultList();
    }
    
     public List<Score> findByUser (String username) {
        return em.createQuery("SELECT s FROM Score s WHERE s.operator.username = :username") //ORDER BY s.rating DESC
                .setParameter("username", username)
                .getResultList();
    }
    
}

