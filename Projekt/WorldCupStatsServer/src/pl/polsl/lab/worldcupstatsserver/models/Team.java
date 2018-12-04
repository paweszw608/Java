package pl.polsl.lab.worldcupstatsserver.models;

/**
 * Class representing a football team
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public class Team {

    /**
     * Name of team
     */
    private String name;

    /**
     * Amount of points collected
     */
    private int points = 0;

    /**
     * One-parameter contructor. Initialize Team object fields
     *
     * @param name name to set
     */
    public Team(String name) {
        this.name = name;
    }

    /**
     * Returns team name
     *
     * @return name name of team
     */
    public String getName() {
        return this.name;
    }

    public int getPoints() {
        return points;
    }

    /**
     * Sets team name
     *
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds points to team's possessions
     *
     * @param newPoints points to add
     */
    void addPoints(int newPoints) {
        points += newPoints;
    }

}
