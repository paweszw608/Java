package pl.polsl.lab.worldcupstats.views;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class representing a view for setting group matches results.
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public class SettingResultsView {

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
    public SettingResultsView(WorldCupStatsView view) {
        this.theView = view;
        this.initializeComponents();
    }
    /**
     * Panel where all components are displayed in
     */
    private final JPanel settingResultsPanel = new JPanel();
    /**
     * Group name Label
     */
    private final JLabel groupNameLabel = new JLabel("Group A");
    /**
     * Label that stores names of teams playing in match number 1
     */
    private final JLabel match1Label = new JLabel();
    /**
     * Label that stores names of teams playing in match number 2
     */
    private final JLabel match2Label = new JLabel();
    /**
     * Label that stores names of teams playing in match number 3
     */
    private final JLabel match3Label = new JLabel();
    /**
     * Label that stores names of teams playing in match number 4
     */
    private final JLabel match4Label = new JLabel();
    /**
     * Label that stores names of teams playing in match number 5
     */
    private final JLabel match5Label = new JLabel();
    /**
     * Label that stores names of teams playing in match number 6
     */
    private final JLabel match6Label = new JLabel();

    /**
     * User input Text Field for result of match number 1
     */
    private final JTextField match1TextField = new JTextField();
    /**
     * User input Text Field for result of match number 2
     */
    private final JTextField match2TextField = new JTextField();
    /**
     * User input Text Field for result of match number 3
     */
    private final JTextField match3TextField = new JTextField();
    /**
     * User input Text Field for result of match number 4
     */
    private final JTextField match4TextField = new JTextField();
    /**
     * User input Text Field for result of match number 5
     */
    private final JTextField match5TextField = new JTextField();
    /**
     * User input Text Field for result of match number 6
     */
    private final JTextField match6TextField = new JTextField();

    /**
     * Button to confirm setting results
     */
    private final JButton confirmResultsButton = new JButton("confirm");
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

        settingResultsPanel.setLayout(new BoxLayout(settingResultsPanel, BoxLayout.Y_AXIS));

        JPanel groupNamePanel = new JPanel();
        groupNamePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        JPanel matchesAndResultsPanel = new JPanel();
        matchesAndResultsPanel.setLayout(new GridLayout(6, 2));
        matchesAndResultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1));

        Font font = new Font("Arial", 1, 18);
        groupNameLabel.setFont(font);

        confirmResultsButton.setFont(font);
        nextGroupButton.setFont(font);
        prevGroupButton.setFont(font);

        match1Label.setFont(font);
        match2Label.setFont(font);
        match3Label.setFont(font);
        match4Label.setFont(font);
        match5Label.setFont(font);
        match6Label.setFont(font);

        match1TextField.setFont(font);
        match2TextField.setFont(font);
        match3TextField.setFont(font);
        match4TextField.setFont(font);
        match5TextField.setFont(font);
        match6TextField.setFont(font);

        groupNamePanel.add(groupNameLabel);

        matchesAndResultsPanel.add(match1Label);
        matchesAndResultsPanel.add(match1TextField);

        matchesAndResultsPanel.add(match2Label);
        matchesAndResultsPanel.add(match2TextField);

        matchesAndResultsPanel.add(match3Label);
        matchesAndResultsPanel.add(match3TextField);

        matchesAndResultsPanel.add(match4Label);
        matchesAndResultsPanel.add(match4TextField);

        matchesAndResultsPanel.add(match5Label);
        matchesAndResultsPanel.add(match5TextField);

        matchesAndResultsPanel.add(match6Label);
        matchesAndResultsPanel.add(match6TextField);

        buttonsPanel.add(confirmResultsButton);
        buttonsPanel.add(nextGroupButton);
        buttonsPanel.add(prevGroupButton);

        settingResultsPanel.add(groupNamePanel);
        settingResultsPanel.add(matchesAndResultsPanel);
        settingResultsPanel.add(buttonsPanel);

    }

    /**
     * Fills Labels with correct matches
     *
     * @param matches String array that stores matches
     */
    public void setMatchLabels(String[] matches) {
        match1Label.setText(matches[0]);
        match2Label.setText(matches[1]);
        match3Label.setText(matches[2]);
        match4Label.setText(matches[3]);
        match5Label.setText(matches[4]);
        match6Label.setText(matches[5]);
    }

    /**
     * Fills Text Fields with correct results
     *
     * @param results String array that stores results
     */
    public void setResultsTextFields(String[] results) {
        match1TextField.setText(results[0]);
        match2TextField.setText(results[1]);
        match3TextField.setText(results[2]);
        match4TextField.setText(results[3]);
        match5TextField.setText(results[4]);
        match6TextField.setText(results[5]);
    }

    /**
     * Returns array of strings from TextFields (that holds match results)
     *
     * @return Returns array of strings from TextFields
     */
    public String[] getTextFieldsContent() {
        String[] content = new String[]{match1TextField.getText(), match2TextField.getText(), match3TextField.getText(),
            match4TextField.getText(), match5TextField.getText(), match6TextField.getText()};

        return content;
    }

    /**
     * Clears Text Fields
     */
    public void clearTextFields() {
        match1TextField.setText(null);
        match2TextField.setText(null);
        match3TextField.setText(null);
        match4TextField.setText(null);
        match5TextField.setText(null);
        match6TextField.setText(null);
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
     * Adds ActionListener for "Add new team" Button
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
        settingResultsPanel.setVisible(false);
        theView.remove(settingResultsPanel);
    }

    /**
     * Show this view. Adds it to Main View frame.
     */
    public void show() {
        theView.add(settingResultsPanel);
    }

}
