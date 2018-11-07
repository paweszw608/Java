package pl.polsl.lab.worldcupstats.models;

import java.util.ArrayList;

/**
 * Class representing a group. Object of Group type stores Vector of Teams
 *
 * @author Paweł Szwajnoch
 * @version 1.0
 */
public class Group {

    /**
     * Name of group (always a single letter)
     */
    private char name;
    /**
     * Vector of teams
     */
    private final ArrayList<Team> teamsVector = new ArrayList<>();

    /**
     * One-parameter constructor. Initialize Group object fields
     *
     * @param name to set
     */
    public Group(char name) {
        this.name = name;
    }

    /**
     * Sets group name
     *
     * @param name name to set
     */
    public void setName(char name) {
        this.name = name;
    }

    /**
     * Returns group name
     *
     * @return group name
     */
    public char getName() {
        return this.name;
    }

    /**
     * Returns an array of names of teams assigned to this group
     *
     * @return array of names of teams
     */
    public String[] getTeamsNames() {
        String[] tmp = new String[teamsVector.size()];
        for (int i = 0; i < teamsVector.size(); i++) {
            tmp[i] = teamsVector.get(i).getName();
        }
        return tmp;
    }

    /**
     * Adds new Team to Vector of teams
     *
     * @param teamName name of team to add
     */
    public void addNewTeam(String teamName) {
        teamsVector.add(new Team(teamName));
    }

    /**
     * Returns amout of teams in this group
     *
     * @return amout of teams in this group
     */
    public int getAmountOfTeams() {
        return teamsVector.size();
    }
}
