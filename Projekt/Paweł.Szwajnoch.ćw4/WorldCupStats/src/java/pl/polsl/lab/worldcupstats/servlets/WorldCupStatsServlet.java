
package pl.polsl.lab.worldcupstats.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.lab.worldcupstats.exceptions.*;
import pl.polsl.lab.worldcupstats.models.*;

/**
 * Class representing servlet managing communication with Model
 *
 * @author Paweł Szwajnoch
 * @version 1.4
 */
public class WorldCupStatsServlet extends HttpServlet {

    /**
     * Model reference
     */
    private final WorldCupStatsModel theModel;

    /**
     * Helps to control switching between views equivalents 0 - Infilling
     * Groups 1 - Setting Results 2 - Reviewing Standings
     */
    private int viewStage = 0;
    /**
     * Switcher if Servlet runned for the very first time
     */
    private String firstRun = "true";

    /**
     * File referenece
     */
    private File historyFile;

    /**
     * Stores messeges for user about errors
     */
    String errorMsg = " ";
    /**
     * Stores the ID ('etiquette') of clicked button
     */
    String btnId = "none";

    /**
     * One-parameter construtor, creates objects
     */
    public WorldCupStatsServlet() {

        theModel = new WorldCupStatsModel();

        historyFile = new File("history.txt");
        try {
            historyFile.createNewFile();
        } catch (IOException ex) {
            errorMsg = "Error while creating file. File may already excist";
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");

        errorMsg = "";

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("firstRun")) {
                firstRun = cookie.getValue();
                break;
            }
        }

        if (firstRun.equals("true")) {
            Cookie cookie = new Cookie("firstRun", "false");
            cookie.setMaxAge(60*30);
            response.addCookie(cookie);
        } else {
            btnId = request.getParameter("btnId");
        }

        try (PrintWriter out = response.getWriter()) {

            String[] teamList = theModel.getActualGroupTeamsList();
            String[] matchesList = theModel.getActualGroupMatches();
            String[] resultsList = {"", "", "", "", "", ""};
            String[] standingsList = theModel.getActualGroupStandings();

            switch (viewStage) {
                case 0: {
                    // <editor-fold defaultstate="collapsed" desc="Infilling Groups switches">

                    switch (btnId) {
                        case "Add team":
                            // add new team clicked:
                            String teamName = request.getParameter("teamName");
                            try {
                                theModel.addTeamToActualGroup(teamName);
                                teamList = theModel.getActualGroupTeamsList();
                                // saving operation to history file
                                Date currentDate = new Date();
                                String msg = currentDate.toString() + "   " + "Team " + teamName + " added to group " + theModel.getGroupName() + "\n";
                                Files.write(Paths.get("history.txt"), msg.getBytes(), StandardOpenOption.APPEND);

                            } catch (TooManyTeamsException | TextContainsForbiddenCharsException ex) {
                                errorMsg = ex.getMessage();
                            } finally {
                                if (theModel.areGroupsFull()) {
                                    // new view:
                                    theModel.resetGroupIndex();
                                    theModel.setEachGroupMatches();
                                    matchesList = theModel.getActualGroupMatches();
                                    viewStage++;
                                }
                            }
                            break;
                        case "Next group": {
                            int actualIndex = theModel.getActualGroupIndex();
                            try {
                                theModel.setActualGroupIndex(actualIndex + 1);
                                teamList = theModel.getActualGroupTeamsList();
                            } catch (WrongGroupIndexException ex) {
                                errorMsg = ex.getMessage();
                            }
                            break;
                        }
                        case "Prevoius group": {
                            int actualIndex = theModel.getActualGroupIndex();
                            try {
                                theModel.setActualGroupIndex(actualIndex - 1);
                                teamList = theModel.getActualGroupTeamsList();
                            } catch (WrongGroupIndexException ex) {
                                errorMsg = ex.getMessage();
                            }
                            break;
                        }
                        default:
                            break;
                    }
                    //</editor-fold>
                    break;
                }
                case 1: {
                    // <editor-fold defaultstate="collapsed" desc="Setting Results switches">
                    switch (btnId) {
                        case "Confirm results": {

                            String[] textFieldsContent = new String[theModel.getActualGroupMatches().length];
                            try {
                                for (int i = 0; i < theModel.getActualGroupMatches().length; i++) {
                                    textFieldsContent[i] = request.getParameter("result" + i);
                                    System.out.println(textFieldsContent[i]);
                                }

                                theModel.setActualGroupResults(textFieldsContent);
                                theModel.setActualGroupResultsConfirmed();
                                resultsList = theModel.getActualGroupResults();
                                errorMsg = "Confirmed succeesfuly";

                                // saving operation to history file
                                Date currentDate = new Date();
                                String msg = currentDate.toString() + "   " + "Results of group " + theModel.getGroupName() + "confrimed succeesfuly\n";
                                Files.write(Paths.get("history.txt"), msg.getBytes(), StandardOpenOption.APPEND);

                            } catch (TextContainsForbiddenCharsException ex) {
                                errorMsg = ex.getMessage();
                            } finally {
                                if (theModel.areGroupsConfirmed()) {
                                    // next view:
                                    theModel.resetGroupIndex();
                                    theModel.calculateEachGroupTeamsPoints();
                                    theModel.sortEachGroupTeams();
                                    standingsList = theModel.getActualGroupStandings();
                                    viewStage++;
                                }
                            }
                            break;
                        }
                        case "Next group": {
                            int actualIndex = theModel.getActualGroupIndex();
                            try {
                                theModel.setActualGroupIndex(actualIndex + 1);
                                matchesList = theModel.getActualGroupMatches();
                                if (theModel.isActualGroupConfirmed()) {
                                    resultsList = theModel.getActualGroupResults();
                                }

                            } catch (WrongGroupIndexException ex) {
                                errorMsg = ex.getMessage();
                            }
                            break;
                        }
                        case "Prevoius group": {
                            int actualIndex = theModel.getActualGroupIndex();
                            try {
                                theModel.setActualGroupIndex(actualIndex - 1);
                                matchesList = theModel.getActualGroupMatches();
                                if (theModel.isActualGroupConfirmed()) {
                                    resultsList = theModel.getActualGroupResults();
                                }

                            } catch (WrongGroupIndexException ex) {
                                errorMsg = ex.getMessage();
                            }
                            break;
                        }
                        default:
                            break;
                    }
                    //</editor-fold>
                    break;
                }
                case 2: {
                    // <editor-fold defaultstate="collapsed" desc="Reviewing Standings switches">
                    switch (btnId) {
                        case "Next group": {

                            int actualIndex = theModel.getActualGroupIndex();
                            try {
                                theModel.setActualGroupIndex(actualIndex + 1);
                                standingsList = theModel.getActualGroupStandings();
                            } catch (WrongGroupIndexException ex) {
                                errorMsg = ex.getMessage();
                            }
                            break;
                        }
                        case "Prevoius group": {
                            int actualIndex = theModel.getActualGroupIndex();
                            try {
                                theModel.setActualGroupIndex(actualIndex - 1);
                                standingsList = theModel.getActualGroupStandings();
                            } catch (WrongGroupIndexException ex) {
                                errorMsg = ex.getMessage();
                            }
                            break;
                        }
                        default:
                            break;
                    }
                    //</editor-fold>
                    break;
                }
                default:
                    break;
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("</head>");
            out.println("<body>");
            // group name:
            out.println("<h1>Group " + theModel.getGroupName() + "</h1>");
            out.println("<form action=WorldCupStatsServlet method=\"GET\">");
            switch (viewStage) {
                case 0: {
                    // <editor-fold defaultstate="collapsed" desc="Infilling Groups HTML stuff">

                    // team name:
                    out.println("<p>Team name: <input type=text size=10 name=teamName></p>");
                    // button add team:
                    out.println("<input type=\"submit\" value=\"Add team\" name=\"btnId\"/>");
                    // list:
                    out.println("<ul>");
                    for (String s : teamList) {
                        out.println("<li>" + s + "</li>");
                    }
                    out.println("</ul>");

                    //</editor-fold> 
                    break;
                }
                case 1: {
                    // <editor-fold defaultstate="collapsed" desc="Setting Results HTML stuff">
                    // list of results:
                    for (int i = 0; i < matchesList.length; i++) {
                        out.println("<p>" + matchesList[i] + " <input type=text size=20 value=\"" + resultsList[i] + "\"" + " name=\"result" + i + "\"" + "></p>");
                    }   // button confirm:
                    out.println("<input type=\"submit\" value=\"Confirm results\" name=\"btnId\"/>");
                    //</editor-fold> 
                    break;
                }
                case 2: {
                    // <editor-fold defaultstate="collapsed" desc="Reviewing standings HTML stuff">
                    out.println("<ol>");
                    for (String s : standingsList) {
                        out.println("<li>" + s + "</li>");
                    }
                    out.println("</ol>");
                    //</editor-fold> 
                    break;
                }
                default:
                    break;
            }

            out.println("<p><input type=\"submit\" value=\"Prevoius group\" name=\"btnId\"/>");
            out.println("<input type=\"submit\" value=\"Next group\" name=\"btnId\"/></p>");
            out.println("</form>");
            // printing error msg:
            out.println("<p>" + errorMsg + "</p><p></p>");
            out.println("</form>");

            // history button:
            out.println(" <form action=History method=\"GET\">");
            out.println("<input type=\"submit\" value=\"Show history\"/>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");

        } catch (IOException ex) {
            Logger.getLogger(WorldCupStatsModel.class.getName()).log(Level.SEVERE, null, ex);
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
