package dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed("app-user")
public class EpisodeBean extends EntityBean<Episode, Long>{

       public EpisodeBean() {
        super(Episode.class);
    }
 
    public void createNewEntry(User user,String series, String season, String name, String description){
        Episode episode = new Episode(user,series,season,name,description);
        em.persist(episode);
    }
    
    public void updateEntry(User user,String series, String season, String name, String description){
        Episode episode = new Episode(user,series,season,name,description);
        em.merge(episode);
    }
    
}
