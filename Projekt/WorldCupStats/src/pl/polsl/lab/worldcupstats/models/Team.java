package pl.polsl.lab.worldcupstats.models;

/**
 * Class representing a football team
 *
 * @author Paweł Szwajnoch
 * @version 1.0
 */
public class Team {

    /**
     * Name of team
     */
    private String name;

    /**
     * One-parameter contructor. Initialize Team object fields
     *
     * @param name name to set
     */
    Team(String name) {
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

    /**
     * Sets team name
     *
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
