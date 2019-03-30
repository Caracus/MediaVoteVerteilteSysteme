/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Series;
import dhbwka.wwi.vertsys.javaee.mediavote.tasks.jpa.Category;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Kategorien.
 */
@Stateless
@RolesAllowed("app-user")
public class SeriesBean extends EntityBean<Series, Long> {

    public SeriesBean() {
        super(Series.class);
    }

    /**
     * Auslesen aller Serien, alphabetisch sortiert.
     *
     * @return Liste mit allen Serien
     */
    public List<Category> findAllSorted() {
        return this.em.createQuery("SELECT s FROM Series s ORDER BY s.name").getResultList();
    }
}
