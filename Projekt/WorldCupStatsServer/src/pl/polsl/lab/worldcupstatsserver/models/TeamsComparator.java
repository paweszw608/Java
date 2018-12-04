package pl.polsl.lab.worldcupstatsserver.models;

import java.util.Comparator;

/**
 * Class representing two Teams objects comparator. Implements Comparator
 * interface to manage objects comparison
 *
 * @see java.util.Comparator
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
public class TeamsComparator implements Comparator<Team> {

    /**
     * Compares two Teams points
     *
     * @param o1 first Team
     * @param o2 second Team
     * @return the value 0 if o2 == o1, a value less than 0 if o1 greater than o2 and a
     * value greater than 0 if o2 greater o1
     */
    @Override
    public int compare(Team o1, Team o2) {
        return Integer.compare(o2.getPoints(), o1.getPoints());
    }

}
