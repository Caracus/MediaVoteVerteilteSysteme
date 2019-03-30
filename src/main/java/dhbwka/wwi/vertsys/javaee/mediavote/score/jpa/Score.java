/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.score.jpa;

import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
@IdClass(ScoreId.class)
public class Score implements Serializable {

    @Id
    //@ManyToOne
    //@NotNull(message = "Bewertungen müssen einem Nutzer zugeordnet sein.")
    private User operator;
    
    @Id
    //@ManyToOne
    //@NotNull(message = "Bewertungen müssen einer Episode zugeordnet sein.")
    private Episode episode;

    //Bewertung von 1 bis 5
    //@Column(nullable=false, precision=1)
    private int rating;
    
    //Konstruktoren

    public Score() {
    }

    public Score(User user, Episode episode, int value) {
        this.operator = user;
        this.episode = episode;
        this.rating = value;
    }
    
    //Getter und Setter

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Hasu und Equals

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
        final Score other = (Score) obj;
        if (this.rating != other.rating) {
            return false;
        }
        if (!Objects.equals(this.operator, other.operator)) {
            return false;
        }
        if (!Objects.equals(this.episode, other.episode)) {
            return false;
        }
        return true;
    }

    
    
    
}
