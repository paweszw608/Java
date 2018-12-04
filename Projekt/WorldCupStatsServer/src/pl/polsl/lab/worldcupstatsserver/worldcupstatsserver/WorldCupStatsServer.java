package pl.polsl.lab.worldcupstatsserver.worldcupstatsserver;

import pl.polsl.lab.worldcupstatsserver.models.WorldCupStatsModel;
import pl.polsl.lab.worldcupstatsserver.exceptions.*;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * WorldCupStatsServerServer is a class which is used to create TCPServer and
 * communicate with the Client
 *
 * @author Paweł Szwajnoch
 * @version 1.3
 */
public class WorldCupStatsServer implements Closeable {

    /**
     * Socket waiting for client connections
     */
    private ServerSocket serverSocket;

    /**
     * Model reference
     */
    WorldCupStatsModel theModel;

    /**
     * Socket representing connection to the client
     */
    private Socket socket;
    /**
     * Input character stream
     */
    private BufferedReader input;
    /**
     * Output character stream
     */
    private PrintWriter output;

    /**
     * One-parameter constructor. Reads properties from config.properties file
     * and run server and create model
     *
     * @throws IOException if cannot run server
     */
    public WorldCupStatsServer() throws IOException {
        Properties serverProperties = new Properties();
        int PORT;
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        serverProperties.load(classLoader.getResourceAsStream("pl/polsl/lab/worldcupstatsserver/properieties/config.properties"));
        PORT = Integer.valueOf(serverProperties.getProperty("port"));
        try {
            theModel = new WorldCupStatsModel();
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            output.println("Cannot run server");
        }
    }

    /**
     * @param args the command line arguments
     *
     */
    public static void main(String[] args) {
        try (WorldCupStatsServer theServer = new WorldCupStatsServer()) {
            while (true) {
                Socket socket = theServer.serverSocket.accept();
                theServer.startListenClient(socket);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Listens fot the client, call appropriate Model methods and send data to
     * Client and messages (succeed/failed)
     *
     * @param socket socket reference
     * @throws IOException if attempt to send or received data from client will
     * fail
     */
    public void startListenClient(Socket socket) throws IOException {
        this.socket = socket;

        output = new PrintWriter(socket.getOutputStream());
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            output.println("000 - World Cup Stats Application server started");
            output.flush();
            while (true) {
                String methodName = input.readLine();
                if (!methodName.isEmpty()) {

                    output.println("001 - Command received: " + methodName);
                    output.flush();
                    switch (methodName) {
                        case "getAmountOfGroups": {
                            output.write(theModel.getAmountOfGroups());
                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "getActualGroupIndex": {
                            output.write(theModel.getActualGroupIndex());
                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "getGroupName": {
                            output.println(theModel.getGroupName());
                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "setActualGroupIndex": {

                            try {
                                int index = input.read();
                                theModel.setActualGroupIndex(index);
                                output.println("010 - Operation: " + methodName + " succeeded");
                                output.flush();
                            } catch (WrongGroupIndexException ex) {
                                output.println("111 " + ex.getMessage());
                                output.flush();
                            }
                            break;
                        }
                        case "resetGroupIndex": {
                            theModel.resetGroupIndex();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "addTeamToActualGroup": {
                            try {
                                String teamName = input.readLine();
                                theModel.addTeamToActualGroup(teamName);
                                output.println("010 - Operation: " + methodName + " succeeded");
                                output.flush();
                            } catch (TooManyTeamsException | TextContainsForbiddenCharsException ex) {
                                output.println("111 " + ex.getMessage());
                                output.flush();
                            }
                            break;
                        }
                        case "getActualGroupTeamsList": {
                            String[] teamList = theModel.getActualGroupTeamsList();
                            output.write(teamList.length);
                            output.flush();
                            for (int i = 0; i < teamList.length; i++) {
                                output.println(teamList[i]);
                            }
                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "areGroupsFull": {
                            output.println(theModel.areGroupsFull());
                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "areGroupsConfirmed": {
                            output.println(theModel.areGroupsConfirmed());
                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "isActualGroupConfirmed": {
                            output.println(theModel.isActualGroupConfirmed());
                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "getActualGroupMatches": {

                            String[] matchList = theModel.getActualGroupMatches();

                            output.write(matchList.length);
                            output.flush();

                            for (int i = 0; i < matchList.length; i++) {
                                output.println(matchList[i]);
                            }

                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;

                        }
                        case "getActualGroupResults": {
                            String[] resultList = theModel.getActualGroupResults();

                            output.write(resultList.length);
                            output.flush();

                            for (int i = 0; i < resultList.length; i++) {
                                output.println(resultList[i]);
                            }

                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "setEachGroupMatches": {
                            theModel.setEachGroupMatches();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "setActualGroupResults": {
                            try {
                                int size = input.read();
                                String[] tmp = new String[size];
                                for (int i = 0; i < size; i++) {
                                    tmp[i] = input.readLine();
                                }
                                theModel.setActualGroupResults(tmp);
                                output.println("010 - Operation: " + methodName + " succeeded");
                                output.flush();
                            } catch (TextContainsForbiddenCharsException ex) {
                                output.println("111 " + ex.getMessage());
                                output.flush();
                            }
                            break;
                        }
                        case "setActualGroupResultsConfirmed": {
                            theModel.setActualGroupResultsConfirmed();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "getActualGroupStandings": {
                            String[] standList = theModel.getActualGroupStandings();

                            output.write(standList.length);
                            output.flush();

                            for (int i = 0; i < standList.length; i++) {
                                output.println(standList[i]);
                            }

                            output.flush();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "calculateEachGroupTeamsPoints": {
                            theModel.calculateEachGroupTeamsPoints();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "sortEachGroupTeams": {
                            theModel.sortEachGroupTeams();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "help": {
                            this.printHelp();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "HELP": {
                            this.printHelp();
                            output.println("010 - Operation: " + methodName + " succeeded");
                            output.flush();
                            break;
                        }
                        case "close": {
                            socket.close();
                            break;
                        }
                        default: {
                            output.println("011 - No such method: " + methodName);
                            output.flush();
                            break;
                        }
                    }
                }

            }
        } catch (IOException ex) {
            output.println("100 - Unable to run this method");
            output.flush();

        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                output.println("101 - Unable to close socket");
                output.flush();
            }
        }
    }

    /**
     * Prints help section to user.
     *
     *
     * @throws IOException when cannot send data
     */
    private void printHelp() throws IOException {
        output.println("Available commands(methods):  , getAmountOfGroups , getActualGroupIndex , getGroupName"
                + " , setActualGroupIndex , resetGroupIndex , addTeamToActualGroup , getActualGroupTeamsList , areGroupsFull"
                + " , isResultTextCorrect , isActualGroupConfirmed , getActualGroupMatches , getActualGroupResults , setEachGroupMatches"
                + " , setActualGroupResults , setActualGroupResultsConfirmed , getActualGroupStandings , calculateEachGroupTeamsPoints"
                + " , sortEachGroupTeams , help , HELP , close");
        output.flush();
    }

    /**
     * This is the override method from closeable interface. The method close
     * the serverSocket
     *
     * @throws IOException if cannot close
     */
    @Override
    public void close() throws IOException {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

}
