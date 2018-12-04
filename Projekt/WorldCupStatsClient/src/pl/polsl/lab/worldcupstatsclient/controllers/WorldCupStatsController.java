package pl.polsl.lab.worldcupstatsclient.controllers;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
import pl.polsl.lab.worldcupstatsclient.exceptions.WrongCommandLineParameterException;
import pl.polsl.lab.worldcupstatsclient.views.*;

/**
 * Controller class for World Cup Stats application
 *
 * @author Paweł Szwajnoch
 * @version 1.3
 */
public final class WorldCupStatsController {

    /**
     * Port variable
     */
    private int PORT = 8888;
    /**
     * Adress variable
     */
    private String adress;
    /**
     * Socket representing connection to the server
     */
    Socket socket;

    /**
     * Input character stream
     */
    private BufferedReader input;
    /**
     * Output character stream
     */
    private PrintWriter output;

    /**
     * InfillingGroupsView reference
     */
    private InfillingGroupsView theInfillingGroupsView;
    /**
     * SettingResultsView reference
     */
    private SettingResultsView theSettingResultsView;
    /**
     * ReviewingStandingsView reference
     */
    private ReviewingStandingsView theReviewingStandingsView;
    /**
     * Main View reference
     */
    private WorldCupStatsView theView;

    /**
     * One-parameter Constructor. Creates proper objects of view and model. Adds
     * buttons ActionListeners. Initialize primary values of compenents
     * displayed by view. Sets primary displayed group to group chosen by user
     * in program parameters. Sets properieties values of port and server
     * address
     *
     *
     * @param groupIndex index of group that user want to be primary displayed
     */
    public WorldCupStatsController(int groupIndex) {

        try {
            this.setPortAndServerProperites();
            this.prepareApp();
            this.theInfillingGroupsView.show();

            try {
                setActualGroupIndex(groupIndex);
            } catch (WrongCommandLineParameterException ex) {
                theView.displayMessage("Couldn't load your group!", "ERROR");
            }
            theInfillingGroupsView.displayGroupName(getGroupName());
        } catch (IOException ex) {
            System.out.println("Cannot connect to the Server :(");
        }

    }

    /**
     * Non-parameter Constructor. Creates proper objects of view and model. Adds
     * buttons ActionListeners. Initialize primary values of compenents
     * displayed by view.
     */
    public WorldCupStatsController() {
        try {
            this.setPortAndServerProperites();
            this.prepareApp();
            this.theInfillingGroupsView.show();
            this.theInfillingGroupsView.displayGroupName('A');
        } catch (IOException ex) {
            System.out.println("Cannot connect to the Server :(");
        }

    }

    /**
     * Creates proper objects of view and model. Adds buttons ActionListeners.
     * primary values of compenents displayed by view.
     */
    private void prepareApp() {
        this.theView = new WorldCupStatsView();
        this.theInfillingGroupsView = new InfillingGroupsView(theView);
        this.theSettingResultsView = new SettingResultsView(theView);
        this.theReviewingStandingsView = new ReviewingStandingsView(theView);

        this.theInfillingGroupsView.addAddNewTeamListener(new AddNewTeamListener());
        this.theInfillingGroupsView.addNextGroupListener(new NextGroupListener(theInfillingGroupsView));
        this.theInfillingGroupsView.addPrevGroupListener(new PrevGroupListener(theInfillingGroupsView));
        this.theSettingResultsView.addNextGroupListener(new NextGroupListener(theSettingResultsView));
        this.theSettingResultsView.addPrevGroupListener(new PrevGroupListener(theSettingResultsView));
        this.theSettingResultsView.addConfirmResultsListener(new ConfirmResultsListener());
        this.theReviewingStandingsView.addNextGroupListener(new NextGroupListener(theReviewingStandingsView));
        this.theReviewingStandingsView.addPrevGroupListener(new PrevGroupListener(theReviewingStandingsView));
    }

    /**
     * Sets properieties values of port and server address
     */
    private void setPortAndServerProperites() throws IOException {

        Properties clientProperties = new Properties();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        clientProperties.load(classLoader.getResourceAsStream("pl/polsl/lab/worldcupstatsclient/properieties/config.properties"));
        adress = clientProperties.getProperty("adress");
        PORT = Integer.valueOf(clientProperties.getProperty("port"));
        socket = new Socket(adress, PORT);
        output = new PrintWriter(socket.getOutputStream());
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        input.readLine();

    }

    /**
     * Sets initial values for Setting Results View
     */
    private void preparePrimarySettingResultsCompontents() {
        WorldCupStatsController.this.resetGroupIndex();
        WorldCupStatsController.this.setEachGroupMatches();

        String[] firstGroupMatches;
        firstGroupMatches = WorldCupStatsController.this.getActualGroupMatches();

        theSettingResultsView.setMatchLabels(firstGroupMatches);

        theInfillingGroupsView.hide();
        theSettingResultsView.show();
    }

    /**
     * Sets initial values for Reviewing Standings View
     */
    private void preparePrimaryReviewingStandingsComponents() {
        WorldCupStatsController.this.resetGroupIndex();
        WorldCupStatsController.this.calculateEachGroupTeamsPoints();
        WorldCupStatsController.this.sortEachGroupTeams();

        String[] firstGroupStandings;
        firstGroupStandings = WorldCupStatsController.this.getActualGroupStandings();
        theReviewingStandingsView.setTeamsLabels(firstGroupStandings);

        theSettingResultsView.hide();
        theReviewingStandingsView.show();
    }

    // MODEL METHODS:
    /**
     * Invokes method from model
     *
     * @param resutls string array of results
     * @throws WrongCommandLineParameterException if results array is wrong
     */
    private void setActualGroupResults(String[] results) throws WrongCommandLineParameterException {
       
        try {
            output.println("setActualGroupResults");
            output.flush();
            input.readLine();
            output.write(results.length);
            output.flush();
            for (int i = 0; i < results.length; i++) {
                output.println(results[i]);
            }
            output.flush();
            String msg = input.readLine();
            if (msg.startsWith("111")) {
                throw new WrongCommandLineParameterException(msg.substring(3));
            }
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
    }

    /**
     * Invokes method from model
     *
     * @return WordlCupStatsModel.isActualGroupConfirmed()
     */
    private boolean isActualGroupConfirmed() {
        try {
            output.println("isActualGroupConfirmed");
            output.flush();
            input.readLine();
            boolean tmp = Boolean.parseBoolean(input.readLine());
            input.readLine();
            return tmp;
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
        return false;
    }

    /**
     * Invokes method from model
     *
     * @return WordlCupStatsModel.areGroupsFull()
     */
    private boolean areGroupsFull() {
        try {
            output.println("areGroupsFull");
            output.flush();
            input.readLine();
            boolean tmp = Boolean.parseBoolean(input.readLine());
            input.readLine();
            return tmp;
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
        return false;
    }

    /**
     * Invokes method from model
     *
     * @return WordlCupStatsModel.areGroupsConfirmed()
     */
    private boolean areGroupsConfirmed() {
        try {
            output.println("areGroupsConfirmed");
            output.flush();
            input.readLine();
            boolean tmp = Boolean.parseBoolean(input.readLine());
            input.readLine();
            return tmp;
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
        return false;
    }

    /**
     * Invokes method from model
     */
    private void calculateEachGroupTeamsPoints() {
        try {
            output.println("calculateEachGroupTeamsPoints");
            output.flush();
            input.readLine();
            input.readLine();
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
    }

    /**
     * Invokes method from model
     */
    private void sortEachGroupTeams() {
        try {
            output.println("sortEachGroupTeams");
            output.flush();
            input.readLine();
            input.readLine();
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
    }

    /**
     * Invokes method from model
     */
    private void setActualGroupResultsConfirmed() {
        try {
            output.println("setActualGroupResultsConfirmed");
            output.flush();
            input.readLine();
            input.readLine();
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
    }

    /**
     * Invokes method from model
     */
    private void setEachGroupMatches() {
        try {
            output.println("setEachGroupMatches");
            output.flush();
            input.readLine();
            input.readLine();

        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
    }

    /**
     * Invokes method from model
     *
     * @return WordlCupStatsModel.getActualGroupStandings()
     */
    private String[] getActualGroupStandings() {
        try {
            int size;
            output.println("getActualGroupStandings");
            output.flush();
            input.readLine();
            size = input.read();

            String[] tmp = new String[size];

            for (int i = 0; i < size; i++) {
                tmp[i] = input.readLine();
            }

            input.readLine();
            return tmp;
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
        return null;
    }

    /**
     * Invokes method from model
     *
     * @return WordlCupStatsModel.getActualGroupResults()
     */
    private String[] getActualGroupResults() {

        try {
            int size;
            output.println("getActualGroupResults");
            output.flush();
            input.readLine();
            size = input.read();
            String[] tmp = new String[size];

            for (int i = 0; i < size; i++) {
                tmp[i] = input.readLine();
            }

            input.readLine();
            return tmp;
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
        return null;
    }

    /**
     * Invokes method from model
     *
     * @return WordlCupStatsModel.getActualGroupMatches()
     */
    private String[] getActualGroupMatches() {

        try {
            int size;
            output.println("getActualGroupMatches");
            output.flush();
            input.readLine();
            size = input.read();

            String[] tmp = new String[size];

            for (int i = 0; i < size; i++) {
                tmp[i] = input.readLine();
            }

            input.readLine();
            return tmp;
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
        return null;
    }

    /**
     * Invokes method from model
     *
     * @return WordlCupStatsModel.getActualGroupTeamsList()
     */
    private String[] getActualGroupTeamsList() {
        try {
            int size;
            output.println("getActualGroupTeamsList");
            output.flush();
            input.readLine();
            size = input.read();
            String[] tmp = new String[size];

            for (int i = 0; i < size; i++) {
                tmp[i] = input.readLine();
            }
            input.readLine();

            return tmp;
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
        return null;
    }

    /**
     * Invokes method from model
     *
     * @param teamName name of team
     * @throws WrongCommandLineParameterException if teamName is wrong
     */
    private void addTeamToActualGroup(String teamName) throws WrongCommandLineParameterException {
        try {
            output.println("addTeamToActualGroup");
            output.flush();
            input.readLine();
            output.println(teamName);
            output.flush();
            String msg = input.readLine();
            // looking for 'wrong parameters' code:
            if (msg.startsWith("111")) {
                throw new WrongCommandLineParameterException(msg.substring(3));
            }
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
    }

    /**
     * Invokes method from model
     */
    private void resetGroupIndex() {
        try {
            output.println("resetGroupIndex");
            output.flush();
            input.readLine();
            input.readLine();
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
    }

    /**
     * Invokes method from model
     *
     * @return WordlCupStatsModel.getGroupName()
     */
    private char getGroupName() {
        try {
            output.println("getGroupName");
            output.flush();
            input.readLine();
            char groupName = input.readLine().charAt(0);
            input.readLine();
            return groupName;
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
        return 'A';
    }

    /**
     * Invokes method from model
     *
     * @return WordlCupStatsModel.getActualGroupIndex()
     */
    private int getActualGroupIndex() {
        try {
            output.println("getActualGroupIndex");
            output.flush();
            input.readLine();
            int index = input.read();
            input.readLine();
            return index;

        } catch (IOException ex) {
            System.out.println("Server connection error");
        }
        return 0;
    }

    /**
     * Invokes method from model
     *
     * @param index new index
     *
     * @throws WrongCommandLineParameterException if index is wrong
     */
    public void setActualGroupIndex(int index) throws WrongCommandLineParameterException {
        try {
            output.println("setActualGroupIndex");
            output.flush();
            input.readLine();
            output.write(index);
            output.flush();
            String msg = input.readLine();
            // looking for 'wrong parameters' code:
            if (msg.startsWith("111")) {
                throw new WrongCommandLineParameterException(msg.substring(3));
            }
        } catch (IOException ex) {
            System.out.println("Server connection error");
        }

    }

    /**
     * ActionListener for submit "Add new team" button at InfillingGroupsView
     * Implements ActionListener interface to manage user interaction.
     *
     * @see java.awt.event.ActionListener
     */
    class AddNewTeamListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            addNewTeam();
        }

        /**
         * Private method that executes operation of adding new team to selected
         * group
         */
        private void addNewTeam() {

            String teamName;
            String[] teamList;

            try {
                teamName = theInfillingGroupsView.getTeamName();
                WorldCupStatsController.this.addTeamToActualGroup(teamName);

                teamList = WorldCupStatsController.this.getActualGroupTeamsList();
                theInfillingGroupsView.displayTeamsList(teamList);
            } catch (WrongCommandLineParameterException ex) {
                theView.displayMessage(ex.getMessage(), "ERROR");
            } finally {
                theInfillingGroupsView.clearTeamNameTextField();
                if (WorldCupStatsController.this.areGroupsFull()) {
                    theView.displayMessage("All groups infilled, moving to the next stage", "DONE");
                    preparePrimarySettingResultsCompontents();

                }
            }

        }
    }

    /**
     * ActionListener for submit "Confirm" button at SettingResultsView
     * Implements ActionListener interface to manage user interaction.
     *
     * @see java.awt.event.ActionListener
     */
    class ConfirmResultsListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            confirmResults();
        }

        /**
         * Private method that executes operation of confirming results of
         * actual group
         */
        private void confirmResults() {

            String[] textFieldsContent;
            try {
                textFieldsContent = theSettingResultsView.getTextFieldsContent();
                WorldCupStatsController.this.setActualGroupResults(textFieldsContent);
                WorldCupStatsController.this.setActualGroupResultsConfirmed();
                theView.displayMessage("Confirmed succeesfuly", "OK");

            } catch (WrongCommandLineParameterException ex) {
                theView.displayMessage(ex.getMessage(), "ERROR");
            } finally {
                if (WorldCupStatsController.this.areGroupsConfirmed()) {
                  
                    theView.displayMessage("All groups confirmed, moving to the next step", "Done");
                    preparePrimaryReviewingStandingsComponents();
                }
            }
        }
    }

    /**
     * ActionListener for submit "Next group" button at WorldCupStatsView *
     * Implements ActionListener interface to manage user interaction.
     *
     * @see java.awt.event.ActionListener
     */
    class NextGroupListener implements ActionListener {

        private final Object view;

        /**
         * One-Parameter Constructor. Sets view object.
         *
         * @param view obj to set
         */
        NextGroupListener(Object view) {
            this.view = view;
        }

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view instanceof InfillingGroupsView) {
                moveNextInfillingGroups();
            } else if (view instanceof SettingResultsView) {
                moveNextSettingResults();
            } else if (view instanceof ReviewingStandingsView) {
                moveNextReviewingStandings();
            }
        }

        /**
         * Private method that moves pointer to the next group Runs where view
         * obj is InfillingGroupsView
         */
        private void moveNextInfillingGroups() {
            try {
                char groupName;
                int actualIndex = WorldCupStatsController.this.getActualGroupIndex();
                String[] teamList;

                WorldCupStatsController.this.setActualGroupIndex(actualIndex + 1);
                groupName = WorldCupStatsController.this.getGroupName();
                theInfillingGroupsView.displayGroupName(groupName);
                theInfillingGroupsView.clearTeamList();
                teamList = WorldCupStatsController.this.getActualGroupTeamsList();
                theInfillingGroupsView.displayTeamsList(teamList);
            } catch (WrongCommandLineParameterException ex) {
                theView.displayMessage("No more groups!", "ERROR");
            }

        }

        /**
         * Private method that moves pointer to the next group Runs where view
         * obj is SettingResultsView
         */
        private void moveNextSettingResults() {
            try {
                char groupName;
                int actualIndex = WorldCupStatsController.this.getActualGroupIndex();
                String[] actualGroupMatches;

                WorldCupStatsController.this.setActualGroupIndex(actualIndex + 1);
                groupName = WorldCupStatsController.this.getGroupName();
                theSettingResultsView.displayGroupName(groupName);

                theSettingResultsView.clearTextFields();
                actualGroupMatches = WorldCupStatsController.this.getActualGroupMatches();
                theSettingResultsView.setMatchLabels(actualGroupMatches);
            } catch (WrongCommandLineParameterException ex) {
                theView.displayMessage("No more groups!", "ERROR");
            }

        }

        /**
         * Private method that moves pointer to the next group Runs where view
         * obj is ReviewingStandingsView
         */
        private void moveNextReviewingStandings() {
            try {
                char groupName;
                int actualIndex = WorldCupStatsController.this.getActualGroupIndex();
                String[] actualGroupStandings;

                WorldCupStatsController.this.setActualGroupIndex(actualIndex + 1);
                groupName = WorldCupStatsController.this.getGroupName();
                theReviewingStandingsView.displayGroupName(groupName);

                actualGroupStandings = WorldCupStatsController.this.getActualGroupStandings();
                theReviewingStandingsView.setTeamsLabels(actualGroupStandings);
            } catch (WrongCommandLineParameterException ex) {
                theView.displayMessage("No more groups!", "ERROR");
            }

        }
    }

    /**
     * ActionListener for submit "Previous group" button at WorldCupStatsView *
     * Implements ActionListener interface to manage user interaction.
     *
     * @see java.awt.event.ActionListener
     */
    class PrevGroupListener implements ActionListener {

        private final Object view;

        /**
         * One-Parameter Constructor. Sets view object.
         *
         * @param view obj to set
         */
        PrevGroupListener(Object view) {
            this.view = view;
        }

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view instanceof InfillingGroupsView) {
                movePrevInfillingGroups();
            } else if (view instanceof SettingResultsView) {
                movePrevSettingResults();
            } else if (view instanceof ReviewingStandingsView) {
                movePrevReviewingStandings();
            }
        }

        /**
         * Private method that moves pointer to the previous group. Runs where
         * view obj is InfillingGroupsView
         */
        private void movePrevInfillingGroups() {

            try {
                char groupName;
                int actualIndex = WorldCupStatsController.this.getActualGroupIndex();
                String[] teamList;

                WorldCupStatsController.this.setActualGroupIndex(actualIndex - 1);
                groupName = WorldCupStatsController.this.getGroupName();
                theInfillingGroupsView.displayGroupName(groupName);
                theInfillingGroupsView.clearTeamList();
                teamList = WorldCupStatsController.this.getActualGroupTeamsList();
                theInfillingGroupsView.displayTeamsList(teamList);
            } catch (WrongCommandLineParameterException ex) {
                theView.displayMessage("No more groups!", "ERROR");
            }

        }

        /**
         * Private method that moves pointer to the previous group. Runs where
         * view obj is SettingResultsView
         */
        private void movePrevSettingResults() {

            try {
                char groupName;
                int actualIndex = WorldCupStatsController.this.getActualGroupIndex();
                String[] actualGroupMatches;

                WorldCupStatsController.this.setActualGroupIndex(actualIndex - 1);
                groupName = WorldCupStatsController.this.getGroupName();
                theSettingResultsView.displayGroupName(groupName);

                if (WorldCupStatsController.this.isActualGroupConfirmed()) {
                    String[] results = WorldCupStatsController.this.getActualGroupResults();
                    theSettingResultsView.setResultsTextFields(results);

                } else {
                    theSettingResultsView.clearTextFields();
                }
                actualGroupMatches = WorldCupStatsController.this.getActualGroupMatches();
                theSettingResultsView.setMatchLabels(actualGroupMatches);
            } catch (WrongCommandLineParameterException ex) {
                theView.displayMessage("No more groups!", "ERROR");
            }

        }

        /**
         * Private method that moves pointer to the previous group. Runs where
         * view obj is ReviewingStandingsView
         */
        private void movePrevReviewingStandings() {
            try {
                char groupName;
                int actualIndex = WorldCupStatsController.this.getActualGroupIndex();
                String[] actualGroupStandings;

                WorldCupStatsController.this.setActualGroupIndex(actualIndex - 1);
                groupName = WorldCupStatsController.this.getGroupName();
                theReviewingStandingsView.displayGroupName(groupName);

                actualGroupStandings = WorldCupStatsController.this.getActualGroupStandings();
                theReviewingStandingsView.setTeamsLabels(actualGroupStandings);
            } catch (WrongCommandLineParameterException ex) {
                theView.displayMessage("No more groups!", "ERROR");
            }

        }
    }

}
