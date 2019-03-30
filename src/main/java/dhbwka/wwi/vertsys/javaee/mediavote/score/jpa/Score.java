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
    @ManyToOne
    @NotNull(message = "Bewertungen müssen einem Nutzer zugeordnet sein.")
    private User user;
    
    @Id
    @ManyToOne
    @NotNull(message = "Bewertungen müssen einer Episode zugeordnet sein.")
    private Episode episode;

    //Bewertung von 1 bis 5
    @Column(nullable=false, precision=1)
    private int value;
    
    //Konstruktoren

    public Score() {
    }

    public Score(User user, Episode episode, int value) {
        this.user = user;
        this.episode = episode;
        this.value = value;
    }
    
    //Getter und Setter

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    
    
}
