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

import dhbwka.wwi.vertsys.javaee.mediavote.common.ejb.UserBean;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Testdatenerzeugung
 * Darf nur einmal aufgerufen werden
 */
@WebServlet(urlPatterns = {"/app/testdata"})
public class CreateTestData extends HttpServlet {
    
    @EJB
    UserBean userBean;
    
    /**
     * GET-Anfrage: Seite anzeigen
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        
        try {
            userBean.signup("testUser1", "test123", "Peter", "Müller");
            userBean.signup("testUser2", "test123", "Moritz", "Meier");
        } catch(UserBean.UserAlreadyExistsException e) {
            
        }
        
        
        response.sendRedirect(WebUtils.appUrl(request, "/app/start/"));
    }

}