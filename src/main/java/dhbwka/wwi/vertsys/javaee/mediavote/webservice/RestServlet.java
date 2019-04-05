package dhbwka.wwi.vertsys.javaee.mediavote.webservice;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/app/rest")
public class RestServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
   
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Client/index.jsp");
        dispatcher.forward(request, response);
  
    }

}