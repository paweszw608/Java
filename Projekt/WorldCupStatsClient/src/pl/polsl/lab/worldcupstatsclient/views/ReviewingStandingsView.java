package pl.polsl.lab.worldcupstatsclient.views;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class representing a view for reviewing standings of groups.
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public class ReviewingStandingsView {

    /**
     * Main View reference
     */
    private final WorldCupStatsView theView;

    /**
     * One-Parameter constructor. Sets WorldCupStatsView object. Invokes method
     * that initialize components (like buttons, labels, etc...)
     *
     * @param view WorldCupStatsView object to set
     */
    public ReviewingStandingsView(WorldCupStatsView view) {
        this.theView = view;
        this.initializeComponents();
    }

    /**
     * Panel where all components are displayed in
     */
    private final JPanel reviewingStandingsPanel = new JPanel();
    /**
     * Group name Label
     */
    private final JLabel groupNameLabel = new JLabel("Group A");
    /**
     * Label that stores first team name and points
     */
    private final JLabel team1Label = new JLabel();
    /**
     * Label that stores second team name and points
     */
    private final JLabel team2Label = new JLabel();
    /**
     * Label that stores third team name and points
     */
    private final JLabel team3Label = new JLabel();
    /**
     * Label that stores fourth team name and points
     */
    private final JLabel team4Label = new JLabel();
    /**
     * Button to end reviewing
     */
    private final JButton confirmResultsButton = new JButton("OK");
    /**
     * Button to move to next group
     */
    private final JButton nextGroupButton = new JButton("next group");
    /**
     * Button to move to previous group
     */
    private final JButton prevGroupButton = new JButton("previous group");

    /**
     * Initialises this view and it's components. Creates layout and sets window
     * elements like buttons, labels...
     */
    private void initializeComponents() {

        reviewingStandingsPanel.setLayout(new BoxLayout(reviewingStandingsPanel, BoxLayout.Y_AXIS));

        JPanel groupNamePanel = new JPanel();
        groupNamePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        JPanel standingsPanel = new JPanel();
        standingsPanel.setLayout(new GridLayout(6, 2));
        standingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1));

        Font font = new Font("Arial", 1, 18);
        groupNameLabel.setFont(font);

        confirmResultsButton.setFont(font);
        nextGroupButton.setFont(font);
        prevGroupButton.setFont(font);

        team1Label.setFont(font);
        team2Label.setFont(font);
        team3Label.setFont(font);
        team4Label.setFont(font);

        groupNamePanel.add(groupNameLabel);

        standingsPanel.add(team1Label);
        standingsPanel.add(team2Label);
        standingsPanel.add(team3Label);
        standingsPanel.add(team4Label);

        buttonsPanel.add(confirmResultsButton);
        buttonsPanel.add(nextGroupButton);
        buttonsPanel.add(prevGroupButton);

        reviewingStandingsPanel.add(groupNamePanel);
        reviewingStandingsPanel.add(standingsPanel);
        reviewingStandingsPanel.add(buttonsPanel);

    }

    /**
     * Fill Labels with correct standings
     *
     * @param standings array of Strings that holds teams
     */
    public void setTeamsLabels(String[] standings) {
        team1Label.setText("1. " + standings[0] + " pts.");
        team2Label.setText("2. " + standings[1] + " pts.");
        team3Label.setText("3. " + standings[2] + " pts.");
        team4Label.setText("4. " + standings[3] + " pts.");
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
     * Adds ActionListener for "Confirm" Button
     *
     * @param listenerForConfirmButton the ActionListener to be added
     */
    public void addConfirmResultsListener(ActionListener listenerForConfirmButton) {

        confirmResultsButton.addActionListener(listenerForConfirmButton);
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
     * Hides this view. Removes it from Main View frame.
     */
    public void hide() {
        reviewingStandingsPanel.setVisible(false);
        theView.remove(reviewingStandingsPanel);
    }
    /**
     * Show this view. Adds it to Main View frame.
     */
    public void show() {
        theView.add(reviewingStandingsPanel);
    }

}
