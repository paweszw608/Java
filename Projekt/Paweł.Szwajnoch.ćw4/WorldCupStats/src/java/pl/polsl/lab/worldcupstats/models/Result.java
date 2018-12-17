package pl.polsl.lab.worldcupstats.models;
/**
 * Class representing result of a match
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public class Result {

    /**
     * Score of first team (amount of goals)
     */
    private int firstTeamScore;
    /**
     * Score of second team (amount of goals)
     */
    private int secondTeamScore;

    /**
     * Two-Parameters constructor. Sets scores.
     *
     * @param firstScore score of first team to set
     * @param secondScore score of second team to set
     */
    Result(int firstScore, int secondScore) {
        this.firstTeamScore = firstScore;
        this.secondTeamScore = secondScore;
    }

    /**
     * Sets first team score
     *
     * @param firstScore score to set
     */
    public void setFirstScore(int firstScore) {
        this.firstTeamScore = firstScore;
    }

    /**
     * Sets second team score
     *
     * @param secondScore score to set
     */
    public void setSecondScore(int secondScore) {
        this.secondTeamScore = secondScore;
    }

    /**
     * Returns value of score of first team
     *
     * @return first team score
     */
    public int getFirstScore() {
        return firstTeamScore;
    }

    /**
     * Returns value of score of second team
     *
     * @return second team score
     */
    public int getSecondScore() {
        return secondTeamScore;
    }

}
