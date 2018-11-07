package pl.polsl.lab.worldcupstats;

import pl.polsl.lab.worldcupstats.controllers.WorldCupStatsController;

/**
 * Runs application
 *
 * @author Paweł Szwajnoch
 * @version 1.0
 */
public class WorldCupStats {

    /**
     * Starting point of World Cup Stats application.
     *
     * Using the command line parameter user can specify which group should be
     * displayed and infilled (with teams) firstly. Parameter should be number
     * (example: 1 means group A)
     *
     *
     * @param args command line parameters
     */
    public static void main(String args[]) {

        WorldCupStatsController controller;

        int parametersNumber = args.length;

        if (parametersNumber == 1) {
            int groupIndex = Integer.parseInt(args[0]);
            controller = new WorldCupStatsController(groupIndex - 1);
        } else {
            controller = new WorldCupStatsController();
        }

    }

}
