package pl.polsl.lab.worldcupstatsserver.models;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class representing a group. Object of Group type stores ArrayList of Teams
 * and ArrayList of its matches
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public class Group {

    /**
     * Name of group (always a single letter)
     */
    private char name;
    /**
     * ArrayList of teams
     */
    private final ArrayList<Team> teamsArrayList = new ArrayList<>();
    /**
     * ArrayList of matches
     */
    private final ArrayList<Match> matchesArrayList = new ArrayList<>();
    /**
     * Swither if group is full
     */
    private boolean isFull;
    /**
     * Swither if user confirmed this group matches results
     */
    private boolean areResultsConfirmed;

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
     * Returns an array of string of teams assigned to this group
     *
     * @return array of strings of teams names
     */
    public String[] getTeamsList() {
        String[] teamsListStrings = new String[teamsArrayList.size()];

        for (int i = 0; i < teamsArrayList.size(); i++) {
            teamsListStrings[i] = teamsArrayList.get(i).getName();
        }
        return teamsListStrings;
    }

    /**
     * Adds new Team to Vector of teams. Checks if group is already
     * infilled(full).
     *
     * @param teamName name of team to add
     */
    public void addNewTeam(String teamName) {
        teamsArrayList.add(new Team(teamName));
        if (teamsArrayList.size() == 4) {
            isFull = true;
        }
    }

    /**
     * Returns true if group has 4 teams (is infilled, full).
     *
     * @return true if group has 4 teams (is infilled, full).
     */
    public boolean isFull() {
        return isFull;
    }

    /**
     * Check if group is confrimed (results are confirmed)
     *
     * @return true if group is confirmed
     */
    public boolean isConfirmed() {
        return areResultsConfirmed;
    }

    /**
     * Sets true if results are confirmed
     *
     * @param confirmed value to set
     */
    public void setConfirmed(boolean confirmed) {
        areResultsConfirmed = confirmed;
    }

    /**
     * Returns amout of teams in this group
     *
     * @return amout of teams in this group
     */
    public int getAmountOfTeams() {
        return teamsArrayList.size();
    }

    /**
     * Sets right configuration of matches between Teams (from teamsArrayList)
     * Adds new Match obj to matchArrayList
     */
    public void setMatches() {

        matchesArrayList.add(new Match(teamsArrayList.get(0), teamsArrayList.get(1)));
        matchesArrayList.add(new Match(teamsArrayList.get(2), teamsArrayList.get(3)));
        matchesArrayList.add(new Match(teamsArrayList.get(1), teamsArrayList.get(3)));
        matchesArrayList.add(new Match(teamsArrayList.get(2), teamsArrayList.get(0)));
        matchesArrayList.add(new Match(teamsArrayList.get(1), teamsArrayList.get(2)));
        matchesArrayList.add(new Match(teamsArrayList.get(3), teamsArrayList.get(0)));

    }

    /**
     * Sets result of particular match from matchesArrayList.
     *
     * @param whichMatch index of match from matchesArrayList to set result to
     * @param firstTeamScore score of first team
     * @param secondTeamScore score of second team
     */
    public void setMatchResult(int whichMatch, int firstTeamScore, int secondTeamScore) {
        matchesArrayList.get(whichMatch).setResult(new Result(firstTeamScore, secondTeamScore));
    }

    /**
     * Returns String array of matches. Form of particular String in array is:
     * "team1 - team2"
     *
     * @return String array of matches
     */
    public String[] getMatches() {
        String[] matchesStrings = new String[matchesArrayList.size()];
       
        for (int i = 0; i < matchesArrayList.size(); i++) {
            matchesStrings[i] = (matchesArrayList.get(i).getFirstTeam().getName()
                    + " - " + matchesArrayList.get(i).getSecondTeam().getName());
        }
        return matchesStrings;
    }

    /**
     * Calculate teams points depending on results. 3 pts if win, 1 if draw, 0
     * if lose
     */
    public void calculateTeamsPoints() {
        matchesArrayList.forEach((match) -> {
            if (match.isDraw()) {
                match.getFirstTeam().addPoints(1);
                match.getSecondTeam().addPoints(1);
            } else {
                match.getWinner().addPoints(3);
            }
        });

    }

    /**
     * Sorting teams in teamsArrayList by their points using TeamsCoparator obj
     */
    public void sortTeams() {
        Collections.sort(teamsArrayList, new TeamsComparator());
    }

    /**
     * Returns results of matches from matchesArrayList
     *
     * @return results of matches
     */
    public ArrayList getMatchesResults() {
        ArrayList<Result> results = new ArrayList<>();
        matchesArrayList.forEach((match) -> {
            results.add(match.getResult());
        });
        return results;
    }

    /**
     * Returns String array of standings. Form of particular String in array is:
     * "team1 pointsOfTeam1"
     *
     * @return String array of standings
     */
    public String[] getStandings() {
        String[] teamsListStrings = new String[teamsArrayList.size()];

        for (int i = 0; i < teamsArrayList.size(); i++) {
            teamsListStrings[i] = teamsArrayList.get(i).getName() + " "
                    + String.valueOf(teamsArrayList.get(i).getPoints());
        }
        return teamsListStrings;
    }

}
