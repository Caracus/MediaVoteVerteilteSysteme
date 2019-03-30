package dhbwka.wwi.vertsys.javaee.mediavote.score.jpa;

import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import java.io.Serializable;
import java.util.Objects;

public class ScoreId implements Serializable {
    private User operator;
    private Episode episode;

    public ScoreId() {
    }
    
    public ScoreId(User user, Episode episode) {
        this.operator = user;
        this.episode = episode;
    }

    public User getUser() {
        return operator;
    }

    public void setUser(User user) {
        this.operator = user;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ScoreId other = (ScoreId) obj;
        if (!Objects.equals(this.operator, other.operator)) {
            return false;
        }
        if (!Objects.equals(this.episode, other.episode)) {
            return false;
        }
        return true;
    }
    
    
    
    
}




