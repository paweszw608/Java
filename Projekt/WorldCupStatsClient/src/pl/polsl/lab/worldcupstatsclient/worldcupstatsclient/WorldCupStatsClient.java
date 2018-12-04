package pl.polsl.lab.worldcupstatsclient.worldcupstatsclient;

import pl.polsl.lab.worldcupstatsclient.controllers.WorldCupStatsController;

/**
 * Runs client application
 *
 * @author Paweł Szwajnoch
 * @version 1.3
 */
public class WorldCupStatsClient {

    /**
     * Reference to the controller
     */
    public WorldCupStatsController controller;

    /**
     * Starting point of World Cup Stats Client application.
     *
     * Creating Controller objects
     *
     * Using the command line parameter user can specify which group should be
     * displayed and infilled (with teams) firstly. Parameter should be number
     * (example: 1 means group A)
     *
     *
     * @param args command line parameters
     */
    public static void main(String[] args) {

        WorldCupStatsClient client = new WorldCupStatsClient();
        int parametersNumber = args.length;
        

        if (parametersNumber == 1) {
            int groupIndex = Integer.parseInt(args[0]);
            client.controller = new WorldCupStatsController(groupIndex - 1);
        } else {
            client.controller = new WorldCupStatsController();
        }
    }

}
