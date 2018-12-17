package pl.polsl.lab.worldcupstats.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class representing servlet responsible for singing user in managment
 *
 * @author Paweł Szwajnoch
 * @version 1.4
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String isLogged = "false";
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            Cookie c = new Cookie("isLogged", "false");
            response.addCookie(c);
        } else {
            for (Cookie c : cookies) {
                if (c.getName().equals("firstRun")) {
                    isLogged = c.getValue();
                    break;
                }
            }
        }

        try (PrintWriter out = response.getWriter()) {
            if ((isLogged.equals("true")) || (login.equals("java") && password.equals("r4"))) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("</head>");
                out.println("<body>");

                out.println("<form action=WorldCupStatsServlet method=\"POST\">");
                out.println("<p>Logged in</p>");
                out.println("<input type=\"submit\" value=\"Ok\" name=\"logged\"/>");
                out.println("</form>");

                out.println("</body>");
                out.println("</html>");
                Cookie c = new Cookie("isLogged", "true");
                c.setMaxAge(60 * 30);
                response.addCookie(c);

            } else {
                String msg = "";
                if (!login.equals("java")) {
                    msg = "login, ";
                }
                if (!password.equals("r4")) {
                    msg += "password, ";
                }
                out.println("Incorrect " + msg + "try again please");

            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Class representing servlet responsible for singing user in managment";
    }// </editor-fold>

}
