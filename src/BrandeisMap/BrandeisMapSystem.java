package BrandeisMap;

import java.io.IOException;
import java.util.Scanner;

public class BrandeisMapSystem {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error Paramters");
            System.exit(0);
        } else {
            if (args[0].equals("route") || args[0].equals("tour")) {
                Route route = new Route();

                if (args[0].equals("tour") && args.length <= 1) {
                    System.out.println("Schedule tour need to specify tour type");
                    System.exit(0);
                }

                Scanner reader = new Scanner(System.in);
                Map brandeisMap = new Map();
                brandeisMap.buildMap(Globals.VERTICES_PATH, Globals.EDGES_PATH);
                brandeisMap.buildMap();


                System.out.print(Globals.WELCOME_PROMOT);
                System.out.print(Globals.ENTER_START_PROMOT);
                Globals.START  = reader.nextLine();

                if (!args[0].equals("tour")) {
                    System.out.print(Globals.ENTER_FINISH_PROMOT);
                    Globals.FINISH  = reader.nextLine();
                }


                String in = "n";
                do {
                    System.out.print(Globals.ENTER_SKATEBOARD_PROMOT);
                    in = reader.nextLine();
                    Globals.SKATE_BOARD = in;

                } while (!(in.equals("y") || in.equals("Y") || in.equals("yes") || in.equals("YES") || in.equals("n") || in.equals("N") || in.equals("no") || in.equals("NO") || in.equals("")));
                boolean skateBoard = false;

                if (in.equals("y") || in.equals("Y") || in.equals("yes") || in.equals("YES"))
                    skateBoard = true;
                else
                    skateBoard = false;

                in = "n";
                do {
                    System.out.print(Globals.ENTER_MINIMIZE_TIME_PROMOT);
                    in = reader.nextLine();
                    Globals.MINIMIZE_TIME = in;
                } while (!(in.equals("y") || in.equals("Y") || in.equals("yes") || in.equals("YES") || in.equals("n") || in.equals("N") || in.equals("no") || in.equals("NO") || in.equals("")));

                boolean minimizeTime = false;
                if (in.equals("y") || in.equals("Y") || in.equals("yes") || in.equals("YES"))
                    minimizeTime = true;
                else
                    minimizeTime = false;
                reader.close();

                if (args[0].equals("route")) {
                    route = brandeisMap.scheduleRoute(Globals.START, Globals.FINISH, skateBoard, minimizeTime);
                    try {
                        route.printRoute(Globals.OUTPUTTOUR_PATH, skateBoard);
                        route.printPixels(Globals.OUTPUTTOUR_ROUTE_PATH, skateBoard,false);
                        route.printPixels(Globals.OUTPUTTOUR_ROUTE_CROPPED_PATH, skateBoard,true);
                        System.out.print("Find out output file at " + Globals.OUTPUT_PATH);
                    } catch (IOException e) {
                        System.out.print("Error happened");
                        e.printStackTrace();
                    }

                } else if (args[0].equals("tour")) {
                    if (args[1].equals("0")) {
                        route = brandeisMap.getSpanningTree(Globals.START, skateBoard, minimizeTime);
                        try {
                            route.printRoute(Globals.OUTPUTTOUR_PATH, skateBoard);
                            route.printPixels(Globals.OUTPUTTOUR_ROUTE_PATH, skateBoard,false);
                            route.printPixels(Globals.OUTPUTTOUR_ROUTE_CROPPED_PATH, skateBoard,true);
                            System.out.print("Find out output file at " + Globals.OUTPUT_PATH);
                        } catch (IOException e) {
                            System.out.print("Error happened");
                            e.printStackTrace();
                        }

                        route = brandeisMap.scheduleTour(Globals.START, skateBoard, minimizeTime);
                        try {
                            route.printRoute(Globals.OUTPUTTOURPP_PATH, skateBoard);
                            route.printPixels(Globals.OUTPUTTOURPP_ROUTE_PATH, skateBoard,false);
                            route.printPixels(Globals.OUTPUTTOURPP_ROUTE_CROPPED_PATH, skateBoard,true);
                            System.out.print("Find out output file at " + Globals.OUTPUT_PATH);
                        } catch (IOException e) {
                            System.out.print("Error happened");
                            e.printStackTrace();
                        }
                    } else if (args[1].equals("1")) {
                        route = brandeisMap.scheduleShortestTour(Globals.START, Globals.FINISH, skateBoard, minimizeTime);
                        try {
                            route.printRoute(Globals.OUTPUTTOUR_PATH, skateBoard);
                            route.printPixels(Globals.OUTPUTTOUR_ROUTE_PATH, skateBoard,false);
                            route.printPixels(Globals.OUTPUTTOUR_ROUTE_CROPPED_PATH, skateBoard,true);
                            System.out.print("Find out output file at " + Globals.OUTPUT_PATH);
                        } catch (IOException e) {
                            System.out.print("Error happened");
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Error Paramters");
                        System.exit(0);
                    }
                }

            } else {
                System.out.println("Error Paramters");
            }

        }
    }
}
