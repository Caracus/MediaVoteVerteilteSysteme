/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mediavote.common.web;

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mediavote.common.jpa.User;
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
 * toComment
 */
@WebServlet(urlPatterns = {"/app/password/"})
public class PasswordServlet extends HttpServlet {
    
    @EJB
    ValidationBean validationBean;
            
    @EJB
    UserBean userBean;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = this.userBean.getCurrentUser();
        HttpSession session = request.getSession();
        
        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/profile/password.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        session.removeAttribute("password_form");
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Formulareingaben auslesen        
        String password1 = request.getParameter("password_password1");
        String password2 = request.getParameter("password_password2");

        // Validierung (leeres Feld erlaubt, da kein Pflichtfeld)
        User user = this.userBean.getCurrentUser();

        List<String> errors = this.validationBean.validate(user);
          
        if (!password1.equals("")) {
            user.setPassword(password1);
            this.validationBean.validate(user.getPassword(), errors);
        }
        
         if (!password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
        // Benutzer updaten 
        if (errors.isEmpty()) {
            try {
                this.userBean.update(user);
            } catch (Exception ex) {
                errors.add(ex.getMessage());
            }
        }
       
        
        // Weiter zur nächsten Seite
        
          if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/start/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("password_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
       
        
        
    }
    
}
