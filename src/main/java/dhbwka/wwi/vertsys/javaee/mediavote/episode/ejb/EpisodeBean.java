package dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed("app-user")
public class EpisodeBean extends EntityBean<Episode, Long>{

       public EpisodeBean() {
        super(Episode.class);
    }
       
       public List<Episode> findBySeries(String series) {
           return em.createQuery("SELECT e FROM Episode e WHERE LOWER(e.series) LIKE :series")
                .setParameter("series", "%" + series.toLowerCase() + "%")
                .getResultList(); 
       }
       
        public List<Episode> findTopAsc() {
           return em.createQuery("SELECT e FROM Episode e ORDER BY e.avgRating ASC")
                .getResultList(); 
    }
 
}
