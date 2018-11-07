package pl.polsl.lab.worldcupstats.controllers;

import java.awt.event.*;
import pl.polsl.lab.worldcupstats.views.WorldCupStatsView;
import pl.polsl.lab.worldcupstats.models.*;

/**
 * Controller class for World Cup Stats application
 *
 * @author Paweł Szwajnoch
 * @version 1.0
 */
public final class WorldCupStatsController {

    /**
     * View reference
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
        this.theModel = new WorldCupStatsModel();

        this.theView.addAddNewTeamListener(new AddNewTeamListener());
        this.theView.addNextGroupListener(new NextGroupListener());
        this.theView.addPrevGroupListener(new PrevGroupListener());

        try {
            theModel.setActualGroupIndex(groupIndex);
            theView.displayGroupName(theModel.getGroupName());
        } catch (WrongGroupIndexException ex) {
            theView.displayErrorMessage("Couldn't load your group!");
        }

    }
    
    /**
     * Non-parameter Constructor. Creates proper objects of view and model. Adds
     * buttons ActionListeners. Initialize primary values of compenents
     * displayed by view. 
     */
    
    public WorldCupStatsController() {
        this.theView = new WorldCupStatsView();
        this.theModel = new WorldCupStatsModel();

        this.theView.addAddNewTeamListener(new AddNewTeamListener());
        this.theView.addNextGroupListener(new NextGroupListener());
        this.theView.addPrevGroupListener(new PrevGroupListener());

        theView.displayGroupName('A');
    }

    /**
     * ActionListener for submit "Add new team" button at WorldCupStatsView
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
                teamName = theView.getTeamName();
                theModel.addTeamToGroup(teamName);
                teamList = theModel.getActualGroupTeamsList();
                theView.displayTeamsList(teamList);
            } catch (TooManyTeamsException | TextContainsForbiddenCharsException ex) {
                theView.displayErrorMessage(ex.getMessage());
            } finally {
                theView.clearTeamNameTextField();
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

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            moveToNextGroup();
        }

        /**
         * Private method that moves pointer to the next group
         */
        private void moveToNextGroup() {
            char groupName;
            int actualIndex = theModel.getActualGroupIndex();
            String[] teamList;

            try {
                theModel.setActualGroupIndex(actualIndex + 1);
                groupName = theModel.getGroupName();
                theView.displayGroupName(groupName);
                theView.clearTeamList();
                teamList = theModel.getActualGroupTeamsList();
                theView.displayTeamsList(teamList);
            } catch (WrongGroupIndexException ex) {
                theView.displayErrorMessage("No more groups!");
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

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            moveToPrevGroup();
        }

        /**
         * Private method that moves pointer to the previous group
         */
        private void moveToPrevGroup() {

            char groupName;
            int actualIndex = theModel.getActualGroupIndex();
            String[] teamList;

            try {
                theModel.setActualGroupIndex(actualIndex - 1);
                groupName = theModel.getGroupName();
                theView.displayGroupName(groupName);
                theView.clearTeamList();
                teamList = theModel.getActualGroupTeamsList();
                theView.displayTeamsList(teamList);
            } catch (WrongGroupIndexException ex) {
                theView.displayErrorMessage("Group A is the first group!");
            }

        }
    }

}
