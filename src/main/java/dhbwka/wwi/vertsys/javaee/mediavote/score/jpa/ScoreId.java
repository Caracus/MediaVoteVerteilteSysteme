package dhbwka.wwi.vertsys.javaee.mediavote.score.jpa;

import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import java.io.Serializable;

public class ScoreId implements Serializable {
    private User user;
    private Episode episode;
   
    public ScoreId(User user, Episode episode) {
        this.user = user;
        this.episode = episode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
    
    
}




