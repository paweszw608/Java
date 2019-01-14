package pl.polsl.lab.worldcupstats.models;
/**
 * Class representing match between two teams
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public class Match {

    /**
     * First Team
     */
    private final Team firstTeam;
    /**
     * Second team
     */
    private final Team secondTeam;
    /**
     * Winner of the match
     */
    private Team winner;
    /**
     * Result of the match
     */
    private Result result;
    /**
     * Switcher if the match ended with draw (example.: 0:0)
     */
    private boolean isDraw;

    /**
     * Two-Parameters constructor. Sets correct teams and primary value of
     * winner variable
     *
     * @param firstTeam first team to set
     * @param secondTeam second teawm to set
     */
    public Match(Team firstTeam, Team secondTeam) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.winner = null;
    }

    /**
     * Returns first team object
     *
     * @return first team object
     */
    public Team getFirstTeam() {
        return this.firstTeam;
    }

    /**
     * Returns second team object
     *
     * @return second team object
     */
    public Team getSecondTeam() {
        return secondTeam;
    }

    /**
     * Returns result of the match
     *
     * @return result of the match
     */
    public Result getResult() {
        return result;
    }

    /**
     * Returns winner of the match
     *
     * @return winner of the match (reference to team object)
     */
    public Team getWinner() {
        return winner;
    }

    /**
     * Sets result of the match
     *
     * @param result result obj to set
     */
    public void setResult(Result result) {
        this.result = result;
        setWinner();
    }

    /**
     * Checks if match ended with draw
     *
     * @return true if match ended with draw (example result: '1:1')
     */
    public boolean isDraw() {
        return isDraw;
    }

    /**
     * Analize match result to find the winner. If draw sets isDraw to true
     */
    private void setWinner() {
        if (result.getFirstScore() > result.getSecondScore()) {
            winner = firstTeam;
        } else if (result.getFirstScore() < result.getSecondScore()) {
            winner = secondTeam;
        } else {
            isDraw = true;
        }
    }
}
