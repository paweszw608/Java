package pl.polsl.lab.worldcupstats.controllers;

import java.awt.event.*;
import pl.polsl.lab.worldcupstats.views.*;
import pl.polsl.lab.worldcupstats.models.*;

/**
 * Controller class for World Cup Stats application
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public final class WorldCupStatsController {

    /**
     * InfillingGroupsView reference
     */
    private final InfillingGroupsView theInfillingGroupsView;
    /**
     * SettingResultsView reference
     */
    private final SettingResultsView theSettingResultsView;
    /**
     * ReviewingStandingsView reference
     */
    private final ReviewingStandingsView theReviewingStandingsView;
    /**
     * Main View reference
     */
    private final WorldCupStatsView theView;
    /**
     * Model reference
     */
    private final WorldCupStatsModel theModel;

    /**
     * One-parameter Constructor. Creates proper objects of view and model. Adds
     * buttons ActionListeners. Initialize primary values of compenents
     * displayed by view. Sets primary displayed group to group chosen by user
     * in program parameters.
     *
     * @param groupIndex index of group that user want to be primary displayed
     */
    public WorldCupStatsController(int groupIndex) {

        this.theView = new WorldCupStatsView();
        this.theInfillingGroupsView = new InfillingGroupsView(theView);
        this.theSettingResultsView = new SettingResultsView(theView);
        this.theReviewingStandingsView = new ReviewingStandingsView(theView);
        this.theModel = new WorldCupStatsModel();

        this.theInfillingGroupsView.addAddNewTeamListener(new AddNewTeamListener());
        this.theInfillingGroupsView.addNextGroupListener(new NextGroupListener(theInfillingGroupsView));
        this.theInfillingGroupsView.addPrevGroupListener(new PrevGroupListener(theInfillingGroupsView));
        this.theSettingResultsView.addNextGroupListener(new NextGroupListener(theSettingResultsView));
        this.theSettingResultsView.addPrevGroupListener(new PrevGroupListener(theSettingResultsView));
        this.theSettingResultsView.addConfirmResultsListener(new ConfirmResultsListener());
        this.theReviewingStandingsView.addNextGroupListener(new NextGroupListener(theReviewingStandingsView));
        this.theReviewingStandingsView.addPrevGroupListener(new PrevGroupListener(theReviewingStandingsView));

        this.theInfillingGroupsView.show();
        try {
            theModel.setActualGroupIndex(groupIndex);
            theInfillingGroupsView.displayGroupName(theModel.getGroupName());
        } catch (WrongGroupIndexException ex) {
            theView.displayMessage("Couldn't load your group!", "ERROR");
        }

    }

    /**
     * Non-parameter Constructor. Creates proper objects of view and model. Adds
     * buttons ActionListeners. Initialize primary values of compenents
     * displayed by view.
     */
    public WorldCupStatsController() {
        this.theView = new WorldCupStatsView();
        this.theInfillingGroupsView = new InfillingGroupsView(theView);
        this.theSettingResultsView = new SettingResultsView(theView);
        this.theReviewingStandingsView = new ReviewingStandingsView(theView);
        this.theModel = new WorldCupStatsModel();

        this.theInfillingGroupsView.addAddNewTeamListener(new AddNewTeamListener());
        this.theInfillingGroupsView.addNextGroupListener(new NextGroupListener(theInfillingGroupsView));
        this.theInfillingGroupsView.addPrevGroupListener(new PrevGroupListener(theInfillingGroupsView));
        this.theSettingResultsView.addNextGroupListener(new NextGroupListener(theSettingResultsView));
        this.theSettingResultsView.addPrevGroupListener(new PrevGroupListener(theSettingResultsView));
        this.theSettingResultsView.addConfirmResultsListener(new ConfirmResultsListener());
        this.theReviewingStandingsView.addNextGroupListener(new NextGroupListener(theReviewingStandingsView));
        this.theReviewingStandingsView.addPrevGroupListener(new PrevGroupListener(theReviewingStandingsView));

        this.theInfillingGroupsView.show();
        this.theInfillingGroupsView.displayGroupName('A');
    }

    private void preparePrimarySettingResultsCompontents() {
        theModel.resetGroupIndex();
        theModel.setEachGroupMatches();

        String[] firstGroupMatches;
        firstGroupMatches = theModel.getActualGroupMatches();
        theSettingResultsView.setMatchLabels(firstGroupMatches);

        theInfillingGroupsView.hide();
        theSettingResultsView.show();
    }

    private void preparePrimaryReviewingStandingsComponents() {
        theModel.resetGroupIndex();
        theModel.calculateEachGroupTeamsPoints();
        theModel.sortEachGroupTeams();

        String[] firstGroupStandings;
        firstGroupStandings = theModel.getActualGroupStandings();
        theReviewingStandingsView.setTeamsLabels(firstGroupStandings);

        theSettingResultsView.hide();
        theReviewingStandingsView.show();
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
                theModel.addTeamToActualGroup(teamName);
                teamList = theModel.getActualGroupTeamsList();
                theInfillingGroupsView.displayTeamsList(teamList);
            } catch (TooManyTeamsException | TextContainsForbiddenCharsException ex) {
                theView.displayMessage(ex.getMessage(), "ERROR");
            } finally {
                theInfillingGroupsView.clearTeamNameTextField();
                if (theModel.areGroupsFull()) {
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
                theModel.setActualGroupResults(textFieldsContent);
                theModel.setActualGroupResultsConfirmed();
                theView.displayMessage("Confirmed succeesfuly", "OK");

            } catch (TextContainsForbiddenCharsException ex) {
                theView.displayMessage(ex.getMessage(), "ERROR");
            } finally {
                if (theModel.areGroupsConfirmed()) {
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
            char groupName;
            int actualIndex = theModel.getActualGroupIndex();
            String[] teamList;

            try {
                theModel.setActualGroupIndex(actualIndex + 1);
                groupName = theModel.getGroupName();
                theInfillingGroupsView.displayGroupName(groupName);
                theInfillingGroupsView.clearTeamList();
                teamList = theModel.getActualGroupTeamsList();
                theInfillingGroupsView.displayTeamsList(teamList);
            } catch (WrongGroupIndexException ex) {
                theView.displayMessage("No more groups!", "ERROR");
            }
        }

        /**
         * Private method that moves pointer to the next group Runs where view
         * obj is SettingResultsView
         */
        private void moveNextSettingResults() {
            char groupName;
            int actualIndex = theModel.getActualGroupIndex();
            String[] actualGroupMatches;
            try {
                theModel.setActualGroupIndex(actualIndex + 1);
                groupName = theModel.getGroupName();
                theSettingResultsView.displayGroupName(groupName);

                theSettingResultsView.clearTextFields();
                actualGroupMatches = theModel.getActualGroupMatches();
                theSettingResultsView.setMatchLabels(actualGroupMatches);
            } catch (WrongGroupIndexException ex) {
                theView.displayMessage("No more groups!", "ERROR");
            }
        }

        /**
         * Private method that moves pointer to the next group Runs where view
         * obj is ReviewingStandingsView
         */
        private void moveNextReviewingStandings() {
            char groupName;
            int actualIndex = theModel.getActualGroupIndex();
            String[] actualGroupStandings;
            try {
                theModel.setActualGroupIndex(actualIndex + 1);
                groupName = theModel.getGroupName();
                theReviewingStandingsView.displayGroupName(groupName);

                actualGroupStandings = theModel.getActualGroupStandings();
                theReviewingStandingsView.setTeamsLabels(actualGroupStandings);

            } catch (WrongGroupIndexException ex) {
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

            char groupName;
            int actualIndex = theModel.getActualGroupIndex();
            String[] teamList;

            try {
                theModel.setActualGroupIndex(actualIndex - 1);
                groupName = theModel.getGroupName();
                theInfillingGroupsView.displayGroupName(groupName);
                theInfillingGroupsView.clearTeamList();
                teamList = theModel.getActualGroupTeamsList();
                theInfillingGroupsView.displayTeamsList(teamList);
            } catch (WrongGroupIndexException ex) {
                theView.displayMessage("Group A is the first group!", "ERROR");
            }

        }

        /**
         * Private method that moves pointer to the previous group. Runs where
         * view obj is SettingResultsView
         */
        private void movePrevSettingResults() {

            char groupName;
            int actualIndex = theModel.getActualGroupIndex();
            String[] actualGroupMatches;

            try {
                theModel.setActualGroupIndex(actualIndex - 1);
                groupName = theModel.getGroupName();
                theSettingResultsView.displayGroupName(groupName);

                if (theModel.isActualGroupConfirmed()) {
                    String[] results = theModel.getActualGroupResults();
                    theSettingResultsView.setResultsTextFields(results);

                } else {
                    theSettingResultsView.clearTextFields();
                }
                actualGroupMatches = theModel.getActualGroupMatches();
                theSettingResultsView.setMatchLabels(actualGroupMatches);
            } catch (WrongGroupIndexException ex) {
                theView.displayMessage("Group A is the first group!", "ERROR");
            }

        }

        /**
         * Private method that moves pointer to the previous group. Runs where
         * view obj is ReviewingStandingsView
         */
        private void movePrevReviewingStandings() {
            char groupName;
            int actualIndex = theModel.getActualGroupIndex();
            String[] actualGroupStandings;
            try {
                theModel.setActualGroupIndex(actualIndex - 1);
                groupName = theModel.getGroupName();
                theReviewingStandingsView.displayGroupName(groupName);

                actualGroupStandings = theModel.getActualGroupStandings();
                theReviewingStandingsView.setTeamsLabels(actualGroupStandings);

            } catch (WrongGroupIndexException ex) {
                theView.displayMessage("Group A is the first group!", "ERROR");
            }
        }
    }

}
