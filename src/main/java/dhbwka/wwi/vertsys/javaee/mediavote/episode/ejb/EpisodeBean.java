package dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Series;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed("app-user")
public class EpisodeBean extends EntityBean<Episode, Long>{

       public EpisodeBean() {
        super(Episode.class);
    }
 
    public void createNewEntry(User user,Series series, int season, String name, String description,int number){
        Episode episode = new Episode(user,series,season,name,description,number);
        em.persist(episode);
    }
    
    public void updateEntry(User user,Series series, int season, String name, String description, int number){
        Episode episode = new Episode(user,series,season,name,description,number);
        em.merge(episode);
    }
    
}
