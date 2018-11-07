package pl.polsl.lab.worldcupstats.views;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * View class for displaying content and components.
 *
 * @author Paweł Szwajnoch
 * @version 1.0
 */
public class WorldCupStatsView extends JFrame {

    /**
     * Group name Label
     */
    private final JLabel groupNameLabel = new JLabel("group A");
    /**
     * User input TextField for team name
     */
    private final JTextField teamNameTextField = new JTextField(10);
    /**
     * Button to add new team to group
     */
    private final JButton addNewTeamButton = new JButton("add team");
    /**
     * Button to move to next group
     */
    private final JButton nextGroupButton = new JButton("next group");
    /**
     * Button to move to previous group
     */
    private final JButton prevGroupButton = new JButton("previous group");
    /**
     * List that contains of teams assigned to current group
     */
    private final JList teamsList = new JList();

    /**
     * Non-parameter Constructor
     */
    public WorldCupStatsView() {
        this.initializeComponents();
    }

    /**
     * Initialises this view and it's components. Creates layout and sets window
     * elemenents like buttons, labels...
     */
    private void initializeComponents() {

        JPanel addTeamPanel = new JPanel();
        addTeamPanel.setLayout(new GridLayout(6, 1));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setResizable(false);

        groupNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        groupNameLabel.setFont(new Font("Arial", 1, 18));
        nextGroupButton.setFont(new Font("Arial", 1, 18));
        prevGroupButton.setFont(new Font("Arial", 1, 18));
        addNewTeamButton.setFont(new Font("Arial", 1, 18));
        teamNameTextField.setFont(new Font("Arial", 1, 28));
        teamsList.setFont(new Font("Arial", 1, 14));

        addTeamPanel.add(groupNameLabel);
        addTeamPanel.add(teamNameTextField);
        addTeamPanel.add(addNewTeamButton);
        addTeamPanel.add(nextGroupButton);
        addTeamPanel.add(prevGroupButton);

        addTeamPanel.add(teamsList);

        this.add(addTeamPanel);
        this.setVisible(true);

    }

    /**
     * Returns the text contained in "Team Name" TextField
     *
     * @return user input from "Team Name" TextField
     */
    public String getTeamName() {
        return teamNameTextField.getText();
    }

    /**
     * Adds ActionListener for "Add new team" Button
     *
     * @param listenerForAddButton the ActionListener to be added
     */
    public void addAddNewTeamListener(ActionListener listenerForAddButton) {

        addNewTeamButton.addActionListener(listenerForAddButton);
    }

    /**
     * Adds ActionListener for "Next group" Button
     *
     * @param listenerForNextButton the ActionListener to be added
     */
    public void addNextGroupListener(ActionListener listenerForNextButton) {

        nextGroupButton.addActionListener(listenerForNextButton);
    }

    /**
     * Adds ActionListener for "Previous group" Button
     *
     * @param listenerForPrevButton the ActionListener to be added
     */
    public void addPrevGroupListener(ActionListener listenerForPrevButton) {

        prevGroupButton.addActionListener(listenerForPrevButton);
    }

    /**
     * Displays error message in a dialog box
     *
     * @param msg text of the message
     */
    public void displayErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "ERROR", 1);
    }

    /**
     * Displays actual group name
     *
     * @param groupName name of the group to display
     */
    public void displayGroupName(char groupName) {
        groupNameLabel.setText("Group " + groupName);
    }

    /**
     * Displays list of teams that are in actual group
     *
     * @param teamsListArray array of teams names to display
     */
    public void displayTeamsList(String[] teamsListArray) {
        teamsList.setListData(teamsListArray);
    }

    /**
     * Empty the "Team Name" user input TextField
     */
    public void clearTeamNameTextField() {
        teamNameTextField.setText(null);
    }

    /**
     * Empty the list of teams
     */
    public void clearTeamList() {
        teamsList.setListData(new Object[0]);
    }

}
