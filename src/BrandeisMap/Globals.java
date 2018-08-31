package BrandeisMap;

public class Globals {
    public static final double WALK_SPEED = 272;
    public static final double WALK_FACTOR_U = 0.9;
    public static final double WALK_FACTOR_F = 1.0;
    public static final double WALK_FACTOR_D = 1.1;
    public static final double SKATE_FACTOR_U = 1.1;
    public static final double SKATE_FACTOR_F = 2.0;
    public static final double SKATE_FACTOR_D = 5.0;
    public static final double STEP_FACTOR_U = 0.5;
    public static final double STEP_FACTOR_D = 0.9;
    public static final double BRIDGE_FACTOR = 1.0;

    public static final String WELCOME_PROMOT = "************* WELCOME TO THE BRANDEIS MAP *************\n";
    public static final String ENTER_START_PROMOT = "Enter start (return to quit): ";
    public static final String ENTER_FINISH_PROMOT = "Enter finish (or return to do a tour): ";
    public static final String ENTER_SKATEBOARD_PROMOT = "Have a skateboard (y/n - default=n)? ";
    public static final String ENTER_MINIMIZE_TIME_PROMOT = "Minimize time (y/n - default=n)? ";

    public static final String VERTICES_PATH = System.getProperty("user.dir") + "/MapDataVertices.txt";
    public static final String EDGES_PATH = System.getProperty("user.dir") + "/MapDataEdges.txt";
    public static final String OUTPUT_PATH = System.getProperty("user.dir") + "output/output.txt";
    public static final String OUTPUT_ROUTE_PATH = System.getProperty("user.dir") + "output/Route.txt";
    public static final String OUTPUT_ROUTE_CROPPED_PATH = System.getProperty("user.dir") + "output/RouteCropped.txt";

    public static final String OUTPUTTOUR_PATH = System.getProperty("user.dir") + "/output/OutputTour.txt";
    public static final String OUTPUTTOUR_ROUTE_PATH = System.getProperty("user.dir") + "/output/Route.txt";
    public static final String OUTPUTTOUR_ROUTE_CROPPED_PATH = System.getProperty("user.dir") + "/output/RouteCropped.txt";

    public static final String OUTPUTTOURPP_PATH = System.getProperty("user.dir") + "/output/OutputTourPP.txt";
    public static final String OUTPUTTOURPP_ROUTE_PATH = System.getProperty("user.dir") + "/output/RoutePP.txt";
    public static final String OUTPUTTOURPP_ROUTE_CROPPED_PATH = System.getProperty("user.dir") + "/output/RouteCroppedPP.txt";

    public static String START = "";
    public static String FINISH = "";
    public static String SKATE_BOARD = "";
    public static String MINIMIZE_TIME = "";

    public static String HEAD() {
        String head = "\n\n" +
                Globals.WELCOME_PROMOT +
                Globals.ENTER_START_PROMOT + START + "\n" +
                Globals.ENTER_FINISH_PROMOT + FINISH + "\n" +
                Globals.ENTER_SKATEBOARD_PROMOT + SKATE_BOARD + "\n" +
                Globals.ENTER_MINIMIZE_TIME_PROMOT + MINIMIZE_TIME + "\n\n";
        return head;
    }
}
