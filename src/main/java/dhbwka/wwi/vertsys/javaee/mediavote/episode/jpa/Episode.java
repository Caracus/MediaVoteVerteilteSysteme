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
 
    @OneToMany
    private List<Score> score = new ArrayList<>();

    @ManyToOne
    private User user;
    
    private String entryOwner;
    private String name;
    private String season;
    private String series;
    private String description;
    private double avgRating;
    
    public Episode(String entryOwner,String series, String season, String name, String description){
        this.entryOwner = entryOwner;
        this.series = series;
        this.season = season;
        this.name = name;
        this.description = description;
    }
    
    public double calculateAvg(){
        double test = 3.0;
        return test ;
    }
        public String getEntryOwner(){
        return this.entryOwner; 
    }
        
    public String getName(){
        return this.name; 
    }
    
    public String getSeason(){
        return this.season; 
    }
        
    public String getSeries(){
        return this.series; 
    }
            
    public String getDescription(){
        return this.description; 
    }
                

}
