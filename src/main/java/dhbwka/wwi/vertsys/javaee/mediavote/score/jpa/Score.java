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
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
public class Score implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull(message = "Bewertungen müssen einem Nutzer zugeordnet sein.")
    private User operator;
    
    @ManyToOne
    @NotNull(message = "Bewertungen müssen einer Episode zugeordnet sein.")
    private Episode episode;

    //Bewertung von 1 bis 5
    //@Column(nullable=false, precision=1)
    @NotNull(message = "Bewertung muss ausgefüllt werden.")
    @Min(1)
    @Max(10)
    private int rating;
    
    //Konstruktoren

    public Score() {
    }

    public Score(User user, Episode episode, int value) {
        this.operator = user;
        this.episode = episode;
        this.rating = value;
    }

    public Score(Long id, User operator, Episode episode, int rating) {
        this.id = id;
        this.operator = operator;
        this.episode = episode;
        this.rating = rating;
    }
    
    //Getter und Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
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

    @Override
    public String toString() {
        return "Score{" + "id=" + id + ", operator=" + operator + ", episode=" + episode + ", rating=" + rating + '}';
    }

    
    
    
}
