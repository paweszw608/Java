package pl.polsl.lab.worldcupstats.models;
import pl.polsl.lab.worldcupstats.exceptions.*;
import java.util.ArrayList;

/**
 * Model class that stores array of Group objects and performs various
 * operations on them.
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public class WorldCupStatsModel {

    /**
     * Index of currently displayed group that user can add new team to
     */
    private int actualGroupIndex = 0;
    /**
     * Quantity of groups in tournament. Value depends of type of the
     * tournament.
     */
    private final int amountOfGroups = 2;
    /**
     * Quantity of teams in each group.
     */
    private final int amountOfTeamsInGroup = 4;
    /**
     * ArrayList of Group objects.
     */
    private final MyContainer<Group> groups = new MyContainer<>();

    /**
     * Non-parameter Constructor
     */
    public WorldCupStatsModel() {
        initializeGroups();
    }

    /**
     * Method that initialize fields of created Groups objects
     */
    private void initializeGroups() {

        groups.add(new Group('A'));
        groups.add(new Group('B'));
       /* groups.add(new Group('C'));
        groups.add(new Group('D'));
        groups.add(new Group('E'));
        groups.add(new Group('F'));
        groups.add(new Group('G'));
        groups.add(new Group('H'));*/
    }

    // general methods, common used: 
    /**
     * Returns amount of groups
     *
     * @return amount of groups
     */
    public int getAmountOfGroups() {
        return amountOfGroups;
    }

    /**
     * Returns actually displayed group index
     *
     * @return actual group index
     */
    public int getActualGroupIndex() {
        return actualGroupIndex;
    }

    /**
     * Returns actual group name
     *
     * @return actual group name
     */
    public char getGroupName() {
        return groups.get(actualGroupIndex).getName();
    }

    /**
     * Set new index if user changes displayed group (presses "next..." or
     * "previous group" Button)
     *
     * @param groupIndex index of actually displayed group
     * @throws WrongGroupIndexException if index is smaller than 0 or bigger
     * that amount of groups. 
     */
    public void setActualGroupIndex(int groupIndex) throws WrongGroupIndexException {
        if (groupIndex < 0 || groupIndex > amountOfGroups - 1) {
            throw new WrongGroupIndexException("No more groups!");
        } else {
            this.actualGroupIndex = groupIndex;
        }
    }

    /**
     * Sets group index to 0 (first group)
     */
    public void resetGroupIndex() {
        actualGroupIndex = 0;
    }

    // methods connected with Infilling Groups:
    /**
     * Add new team to currently displayed group
     *
     * @param teamName name of team to add
     * @throws TooManyTeamsException if user try to add team to full filled
     * group
     * @throws TextContainsForbiddenCharsException if user try to add team which
     * name conatians of numbers, commas, etc...
     */
    public void addTeamToActualGroup(String teamName) throws TooManyTeamsException, TextContainsForbiddenCharsException {

        if (!isTeamNameCorrect(teamName)) {
            throw new TextContainsForbiddenCharsException("Wrong team name!");
        }

        if (groups.get(actualGroupIndex).getAmountOfTeams() > amountOfTeamsInGroup - 1) {
            throw new TooManyTeamsException("There are already 4 teams in this group");
        }

        groups.get(actualGroupIndex).addNewTeam(teamName);
    }

    /**
     * Returns array of names of teams assigned to actual group
     *
     * @return array of teams names
     */
    public String[] getActualGroupTeamsList() {
        return groups.get(actualGroupIndex).getTeamsList();

    }

    /**
     * Checks if String consists of only letters
     *
     * @param name String to check
     * @return true if String is only text (no numbers, commas, etc...)
     */
    private boolean isTeamNameCorrect(String name) {
        if (name.isEmpty()) {
            return false;
        } else {
            char[] chars = name.toCharArray();

            for (char c : chars) {
                if (!Character.isLetter(c)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Checks if every groups has 4 teams
     *
     * @return true if every group has 4 teams (is full)
     */
    public boolean areGroupsFull() {

        for (Group g : groups) {
            if (!g.isFull()) {
                return false;
            }
        }
        return true;
    }

    // methods connected with Setting Results:
    /**
     * Checks if String consists only of numbers
     *
     * @param result String to check
     * @return true if String is only digit (no letters, commas, etc...)
     */
    private boolean isResultTextCorrect(String result) {
        if (result.isEmpty()) {
            return false;
        } else {
            char[] chars = result.toCharArray();

            for (char c : chars) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Checks if every group results are confirmed
     *
     * @return true if every group is confirmed
     */
    public boolean areGroupsConfirmed() {
        for (Group g : groups) {
            if (!g.isConfirmed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if actual group results are confirmed
     *
     * @return true if actual group is confirmed
     */
    public boolean isActualGroupConfirmed() {
        return groups.get(actualGroupIndex).isConfirmed();
    }

    /**
     * Returns String array of matches of actual group. Form of particular
     * String in array is: "team1 - team2"
     *
     * @return String array of matches of actual group
     */
    public String[] getActualGroupMatches() {
        return groups.get(actualGroupIndex).getMatches();
    }

    /**
     * Returns String array of results of actual group. Form of particular
     * String in array is: "score1:score2".
     *
     * @return String array of results of actual group
     */
    public String[] getActualGroupResults() {
        ArrayList<Result> resultsArrayList = groups.get(actualGroupIndex).getMatchesResults();
        String[] tmp = new String[resultsArrayList.size()];
        for (int i = 0; resultsArrayList.size() > i; i++) {
            tmp[i] = (String.valueOf(resultsArrayList.get(i).getFirstScore())
                    + ":" + String.valueOf(resultsArrayList.get(i).getSecondScore()));
        }
        return tmp;
    }

    /**
     * Sets matches for each of groups
     */
    public void setEachGroupMatches() {
        groups.forEach((g) -> {
            g.setMatches();
        });
    }

    /**
     * Evaluate user input String array. Checks if confirmed results are ready
     * to set to the matches. If OK then particular results are set to correct
     * matches.
     *
     * @param textFieldsContent String array of user input results
     * @throws TextContainsForbiddenCharsException if form of any result String
     * is wrong
     */
    public void setActualGroupResults(String[] textFieldsContent) throws TextContainsForbiddenCharsException {

        int firstTeamScore, secondTeamScore;
        String[] parts;
        boolean succeed;

        for (int i = 0; i < textFieldsContent.length; i++) {
            succeed = false;
            if (textFieldsContent[i].contains(":") && !textFieldsContent[i].startsWith(":") && !textFieldsContent[i].endsWith(":")) {
                parts = textFieldsContent[i].split(":");

                if (isResultTextCorrect(parts[0]) && isResultTextCorrect(parts[1])) {
                    firstTeamScore = Integer.parseInt(parts[0]);
                    secondTeamScore = Integer.parseInt(parts[1]);

                    groups.get(actualGroupIndex).setMatchResult(i, firstTeamScore, secondTeamScore);
                    succeed = true;
                }
            }
            if (!succeed) {
                throw new TextContainsForbiddenCharsException("Wrong result form in Text Field number: "
                        + String.valueOf(i + 1) + " Result should have form of: FIRST:SECOND!");
            }
        }
    }

    /**
     * Make actual group results confirmed (sets true)
     */
    public void setActualGroupResultsConfirmed() {
        groups.get(actualGroupIndex).setConfirmed(true);
    }

    //methods connected with Reviewing Standings:
    /**
     * Returns String array of actual group standings. Form of particular String
     * in array is: "team1 pointsOfTeam1"
     *
     * @return String array of actual group standings
     */
    public String[] getActualGroupStandings() {
        return groups.get(actualGroupIndex).getStandings();
    }

    /**
     * In every group calculates how many points teams should have
     */
    public void calculateEachGroupTeamsPoints() {
        groups.forEach((g) -> {
            g.calculateTeamsPoints();
        });
    }

    /**
     * In evert group sorts teams by its points
     */
    public void sortEachGroupTeams() {
        groups.forEach((g) -> {
            g.sortTeams();
        });
    }

}
