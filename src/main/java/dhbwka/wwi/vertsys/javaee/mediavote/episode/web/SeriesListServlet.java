/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.episode.web;

import dhbwka.wwi.vertsys.javaee.mediavote.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb.EpisodeBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb.SeriesBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Series;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Seite zum Anzeigen und Bearbeiten der Kategorien. Die Seite besitzt ein
 * Formular, mit dem ein neue Kategorie angelegt werden kann, sowie eine Liste,
 * die zum Löschen der Kategorien verwendet werden kann.
 */
@WebServlet(urlPatterns = {"/app/episodes/series/"})
public class SeriesListServlet extends HttpServlet {

    @EJB
    SeriesBean seriesBean;

    @EJB
    EpisodeBean episodeBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Serien ermitteln
        request.setAttribute("series", this.seriesBean.findAllSorted());

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/episodes/series_list.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("series_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen        
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                this.createCategory(request, response);
                break;
            case "delete":
                //this.deleteCategories(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Kategorie anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");

        Series series = new Series(name);
        List<String> errors = this.validationBean.validate(series);

        // Neue Kategorie anlegen
        if (errors.isEmpty()) {
            this.seriesBean.saveNew(series);
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("categories_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

}