/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa;

import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mediavote.score.jpa.Score;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
public class Episode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @OneToMany(mappedBy="episode")
    private List<Score> score = new ArrayList<>();

    @ManyToOne
    private User user;
    
    private String name;
    private String series;
    private String season;
    private int number;
    private String description;
    private double avgRating;
    
    public Episode(){
        
    }
    
     public Episode(User user, String name, String series, String season, int number, String description) {
        this.user = user;
        this.name = name;
        this.series = series;
        this.season = season;
        this.number = number;
        this.description = description;
    }

    public Episode(Long id, User user, String name, String series, String season, int number, String description, double avgRating) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.series = series;
        this.season = season;
        this.number = number;
        this.description = description;
        this.avgRating = avgRating;
    }
    
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Score> getScore() {
        return score;
    }

    public void setScore(List<Score> score) {
        this.score = score;
    }
    
    public void addScore(Score score) {
        this.score.getClass();
        score.getClass();
        this.score.add(score);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
    
    public double calculateAvgRating() {
        
        double sum = 0;
        int i = 0;
        for(Score s : this.score) {
            sum = sum + s.getRating();
            i++;
        }
        
        if(i == 0) {
            this.avgRating = 100.0;
            return 100.0;
        }
        double avg = sum / i; 

       // double avg = 3.0;
        this.avgRating = avg;
        return avg;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }            

}
