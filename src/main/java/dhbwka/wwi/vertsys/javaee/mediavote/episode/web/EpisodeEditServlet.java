package dhbwka.wwi.vertsys.javaee.mediavote.episode.web;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.mediavote.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.ejb.EpisodeBean;
import dhbwka.wwi.vertsys.javaee.mediavote.episode.jpa.Episode;
import dhbwka.wwi.vertsys.javaee.mediavote.tasks.ejb.CategoryBean;
import dhbwka.wwi.vertsys.javaee.mediavote.tasks.jpa.Task;
import dhbwka.wwi.vertsys.javaee.mediavote.tasks.jpa.TaskStatus;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/app/tasks/task/*")
public class EpisodeEditServlet extends HttpServlet {

    @EJB
    EpisodeBean episodeBean;
    
    @EJB
    CategoryBean categoryBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;
    
@Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        request.setAttribute("categories", this.categoryBean.findAllSorted());
        request.setAttribute("statuses", EpisodeStatus.values());

        // Zu bearbeitende Aufgabe einlesen
        HttpSession session = request.getSession();

        Episode episode = this.getRequestedEpisode(request);
        request.setAttribute("edit", episode.getId() != 0);
                                
        if (session.getAttribute("episode_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("episode_form", this.createEpisodeForm(episode));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/tasks/task_edit.jsp").forward(request, response);
        
        session.removeAttribute("episode_form");
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
            case "save":
                this.saveEpisode(request, response);
                break;
            case "delete":
                this.deleteEpisode(request, response);
                break;
        }
    }
    
  private void saveEpisode(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String episodeCategory = request.getParameter("episode_category");
        String episodeDueDate = request.getParameter("episode_due_date");
        String episodeDueTime = request.getParameter("episode_due_time");
        String episodeStatus = request.getParameter("episode_status");
        String episodeShortText = request.getParameter("episode_short_text");
        String episodeLongText = request.getParameter("episode_long_text");

        Episode episode = this.getRequestedEpisode(request);

        if (episodeCategory != null && !episodeCategory.trim().isEmpty()) {
            try {
                episode.setCategory(this.categoryBean.findById(Long.parseLong(episodeCategory)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        Date dueDate = WebUtils.parseDate(episodeDueDate);
        Time dueTime = WebUtils.parseTime(episodeDueTime);

        if (dueDate != null) {
            episode.setDueDate(dueDate);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (dueTime != null) {
            episode.setDueTime(dueTime);
        } else {
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }

        try {
            episode.setStatus(EpisodeStatus.valueOf(episodeStatus));
        } catch (IllegalArgumentException ex) {
            errors.add("Der ausgewählte Status ist nicht vorhanden.");
        }

        episode.setShortText(episodeShortText);
        episode.setLongText(episodeLongText);

        this.validationBean.validate(episode, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.EpisodeBean.update(episode);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/tasks/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("episode_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    /**
     * Aufgerufen in doPost: Vorhandene Aufgabe löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteEpisode(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Episode episode = this.getRequestedEpisode(request);
        this.EpisodeBean.delete(episode);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/tasks/list/"));
    }

    /**
     * Zu bearbeitende Aufgabe aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Zu bearbeitende Aufgabe
     */
    private Episode getRequestedEpisode(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Episode episode = new Episode();
        episode.setOwner(this.userBean.getCurrentUser());
        episode.setDueDate(new Date(System.currentTimeMillis()));
        episode.setDueTime(new Time(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String episodeId = request.getPathInfo();

        if (episodeId == null) {
            episodeId = "";
        }

        episodeId = episodeId.substring(1);

        if (episodeId.endsWith("/")) {
            episodeId = episodeId.substring(0, episodeId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            episode = this.episodeBean.findById(Long.parseLong(episodeId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return episode;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param task Die zu bearbeitende Aufgabe
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createEpisodeForm(Episode episode) {
        Map<String, String[]> values = new HashMap<>();

        values.put("episode_owner", new String[]{
            episode.getOwner().getUsername()
        });

        if (episode.getCategory() != null) {
            values.put("episode_category", new String[]{
                "" + episode.getCategory().getId()
            });
        }

        values.put("episode_due_date", new String[]{
            WebUtils.formatDate(episode.getDueDate())
        });

        values.put("episode_due_time", new String[]{
            WebUtils.formatTime(episode.getDueTime())
        });

        values.put("episode_status", new String[]{
            episode.getStatus().toString()
        });

        values.put("episode_short_text", new String[]{
            episode.getShortText()
        });

        values.put("episode_long_text", new String[]{
            episode.getLongText()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
