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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class representing servlet managing history of operations
 *
 *
 * @author Paweł Szwajnoch
 * @version 1.5
 */
public class History extends HttpServlet {

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

        try (PrintWriter out = response.getWriter()) {
            try {
                ResultSet rs = null;
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
                        java.sql.Statement statement = con.createStatement();
                        rs = statement.executeQuery("SELECT * FROM history");
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>History</title>");
                out.println("</head>");
                out.println("<body>");
                if (rs != null) {
                    while (rs.next()) {

                        out.println(rs.getString("date") + "  ");
                        out.println(rs.getString("operation") + " : ");
                        out.println(rs.getString("comment") + "<br>");
                        out.println();
                    }
                }
                out.println("</body>");
                out.println("</html>");
                
                if(con != null) con.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        } catch (IOException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
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
        return "Short description";
    }// </editor-fold>

}
