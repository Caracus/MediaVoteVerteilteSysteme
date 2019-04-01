package dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed("app-user")
public class EpisodeBean extends EntityBean<Episode, Long>{

       public EpisodeBean() {
        super(Episode.class);
    }
 
   
}
