package pl.polsl.lab.worldcupstats.models;

/**
 * Model class that stores array of Group objects and performs various
 * operations on them.
 *
 * @author Paweł Szwajnoch
 * @version 1.0
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
    private final int amountOfGroups = 8;
    /**
     * Quantity of teams in each group.
     */
    private final int amountOfTeamsInGroup = 4;
    /**
     * Array of Group objects.
     */
    private final Group[] groups = new Group[amountOfGroups];

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

        groups[0] = new Group('A');
        groups[1] = new Group('B');
        groups[2] = new Group('C');
        groups[3] = new Group('D');
        groups[4] = new Group('E');
        groups[5] = new Group('F');
        groups[6] = new Group('G');
        groups[7] = new Group('H');
    }

    /**
     * Add new team to currently displayed group
     *
     * @param teamName name of team to add
     * @throws TooManyTeamsException if user try to add team to full filled
     * group
     * @throws TextContainsForbiddenCharsException if user try to add team which
     * name conatians of numbers, commas, etc...
     */
    public void addTeamToGroup(String teamName) throws TooManyTeamsException, TextContainsForbiddenCharsException {

        if (teamName.isEmpty() || !isAlpha(teamName)) {
            throw new TextContainsForbiddenCharsException("Wrong team name!");
        }

        if (groups[actualGroupIndex].getAmountOfTeams() > amountOfTeamsInGroup - 1) {
            throw new TooManyTeamsException("There are already 4 teams in this group");
        }

        groups[actualGroupIndex].addNewTeam(teamName);
    }

    /**
     * Returns amount of groups
     *
     * @return amount of groups
     */
    public int getAmountOfGroups() {
        return amountOfGroups;
    }

    /**
     * Returns actual group name
     *
     * @return actual group name
     */
    public char getGroupName() {
        return groups[actualGroupIndex].getName();
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
     * Returns array of names of teams assigned to actual group
     *
     * @return array of teams names
     */
    public String[] getActualGroupTeamsList() {
        return groups[actualGroupIndex].getTeamsNames();
    }

    /**
     * Set new index if user changes displayed group (presses "next..." or
     * "previous group" Button)
     *
     * @param groupIndex index of actually displayed group
     * @throws WrongGroupIndexException if index is smaller than 0 or bigger
     * that amount of groups
     */
    public void setActualGroupIndex(int groupIndex) throws WrongGroupIndexException {
        if (groupIndex < 0 || groupIndex > amountOfGroups - 1) {
            throw new WrongGroupIndexException();
        }
        this.actualGroupIndex = groupIndex;
    }

    /**
     * Checks if String consists of only letters
     *
     * @param name String to check
     * @return true if String is only text (no numbers, commas, etc...)
     */
    private boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

}
