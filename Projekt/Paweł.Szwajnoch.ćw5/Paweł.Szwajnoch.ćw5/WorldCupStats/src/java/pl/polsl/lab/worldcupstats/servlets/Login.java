package pl.polsl.lab.worldcupstats.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @version 1.5
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Connects to database and creates proper tables
     */
    @Override
    public void init() {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            return;
        }

        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/WorldCupStatsDatabase", this.getInitParameter("user"), this.getInitParameter("password"));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        if (con != null) {
            try {
                java.sql.Statement statement1 = con.createStatement();
                statement1.executeUpdate("CREATE TABLE history "
                        + "(id INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
                        + "date timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                        + "operation VARCHAR(100), comment VARCHAR(50), PRIMARY KEY(id))");

            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }

            try {
                java.sql.Statement statement2 = con.createStatement();
                statement2.executeUpdate("CREATE TABLE users "
                        + "(id INTEGER GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), "
                        + "login VARCHAR(100) NOT NULL UNIQUE, password VARCHAR(50) NOT NULL, PRIMARY KEY(id))");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

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
        try {
            response.setContentType("text/html;charset=UTF-8");

            ResultSet rs = null;
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            if (login == null || password == null) {
                login = password = " ";
            }

            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                System.err.println(ex.getMessage());
                return;
            }

            Connection con = null;
            try {
                con = DriverManager.getConnection("jdbc:derby://localhost:1527/WorldCupStatsDatabase", this.getInitParameter("user"), this.getInitParameter("password"));
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
            if (con != null) {
                try {
                    java.sql.Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    rs = statement.executeQuery("SELECT * FROM users u WHERE u.login = '" + login + "' AND u.password = '" + password + "'");
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
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

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("</head>");
                out.println("<body>");
                if (rs != null) {
                    if (rs.first() || (login.equals("admin") && password.equals("admin")) || isLogged.equals("true")) {
                        out.println("<form action=WorldCupStatsServlet method=\"POST\">");
                        out.println("<p>Logged in as " + login + "</p>");
                        out.println("<input type=\"submit\" value=\"Ok\" name=\"loggedin\"/>");
                        out.println("</form>");

                        Cookie c = new Cookie("isLogged", "true");
                        c.setMaxAge(60 * 30);
                        response.addCookie(c);
                        Cookie c1 = new Cookie("firstRun", "true");
                        c.setMaxAge(60 * 30);
                        response.addCookie(c1);

                    } else {
                        String msg = "";
                        if (!login.equals("admin")) {
                            msg = "login, ";
                        }
                        if (!password.equals("admin")) {
                            msg += "password, ";
                        }
                        out.println("Incorrect " + msg + "try again please");

                    }
                }

                out.println("</body>");
                out.println("</html>");
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
