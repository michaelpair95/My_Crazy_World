/**
 * Created by michaelpair on 2/24/14.
 */
import java.util.Scanner;

public class Game {

    //
    // Public
    //

    // Globals
    public static final boolean DEBUGGING  = false; // Debugging flag.
    public static final int MAX_LOCALES = 9;    // Total number of rooms/locations we have in the game.
    public static int currentLocale = 0;        // Player starts in locale 0.
    public static String command;               // What the player types as he or she plays the game.
    public static boolean stillPlaying = true; // Controls the game loop.
    public static Locale[] locations;           // An uninitialized array of type Locale. See init() for initialization.
    public static int[][]  nav;                 // An uninitialized array of type int int.
    public static int moves = 0;                // Counter of the player's moves.
    public static int score = 0;                // Tracker of the player's score.

    public static void main(String[] args) {
        if (DEBUGGING) {
            // Display the command line args.
            System.out.println("Starting with args:");
            for (int i = 0; i < args.length; i++) {
                System.out.println(i + ":" + args[i]);
            }
        }

        // Set starting locale, if it was provided as a command line parameter.
        if (args.length > 0) {
            try {
                int startLocation = Integer.parseInt(args[0]);
                // Check that the passed-in value for startLocation is within the range of actual locations.
                if ( startLocation >= 0 && startLocation <= MAX_LOCALES) {
                    currentLocale = startLocation;
                } else {
                    System.out.println("WARNING: passed-in starting location (" + args[0] + ") is out of range.");
                }
            } catch(NumberFormatException ex) {
                System.out.println("WARNING: Invalid command line arg: " + args[0]);
                if (DEBUGGING) {
                    System.out.println(ex.toString());
                }
            }
        }

        // Get the game started.
        init();
        updateDisplay();

        // Game Loop
        while (stillPlaying) {
            getCommand();
            navigate();
            updateDisplay();
        }

        // We're done. Thank the player and exit.
        System.out.println("Thank you for playing.");

    }

    //
    // Private
    //


    private static void init() {
        // Initialize any uninitialized globals.
        command = new String();
        stillPlaying = true;   // TODO: Do we need this?
        System.out.println("Mikes Crazy World!");
        System.out.println("To see a list of commands, type \"h\" or \"help\" or");
        System.out.println("------------------");

        // Set up the location instances of the Locale class.
        Locale loc0 = new Locale(0);
        loc0.setName("A Shack");
        loc0.setDesc("Its a very small shack");

        Locale loc1 = new Locale(1);
        loc1.setName("Erebor");
        loc1.setDesc("What happened to all the gold?");

        Locale loc2 = new Locale(2);
        loc2.setName("The Library");
        loc2.setDesc("It's so boring here...");

        Locale loc3 = new Locale(3);
        loc3.setName("The SUPER Market");
        loc3.setDesc("Wanna buy some mutten?");

        Locale loc4 = new Locale(4);
        loc4.setName("Rainbow Road");
        loc4.setDesc("Wiiiiiiiiiiiiii!");

        Locale loc5 = new Locale(5);
        loc5.setName("Eetzeek Hagadol");
        loc5.setDesc("Shalom habibi, mah aht rotsah?");

        Locale loc6 = new Locale(6);
        loc6.setName("Magick Shoppe");
        loc6.setDesc("You can get enchanted stuffs here");

        Space loc7 = new Space(7);
        loc7.setName("Kennedy Space Center");
        loc7.setDesc("There's a shuttle ready to launch");
        loc7.setNearestPlanet("Planet Vegeta");

        Time loc8 = new Time(8);
        loc8.setName("The DeLorean");
        loc8.setDesc("The time is set to ____"); //TODO: find a time and date to use
        loc8.setNearestYear("__1984?___");


        // Set up the location array.
        locations = new Locale[9];
        locations[0] = loc0;
        locations[1] = loc1;
        locations[2] = loc2;
        locations[3] = loc3;
        locations[4] = loc4;
        locations[5] = loc5;
        locations[6] = loc6;
        locations[7] = loc7;
        locations[8] = loc8;

        if (DEBUGGING) {
            System.out.println("All game locations:");
            for (int i = 0; i < locations.length; ++i) {
                System.out.println(i + ":" + locations[i].toString());
            }
        }
        // Set up the navigation matrix.
        nav = new int[][] {
        /*N   S   E  W */
        /*0   1   2  3 */
        { 3, -1,  1, -1},
        { 2,  6, -1,  0},
        { 7,  1,  5,  3},
        {-1,  0,  2,  4},
        { 8, -1,  3, -1},
        {-1, -1, -1,  2},
        { 1, -1, -1, -1},
        {-1,  2, -1, -1},
        {-1,  4, -1, -1}
        };

        //createMagicItems();
    }

    private static void updateDisplay() {
        System.out.println(locations[currentLocale].getText());
        System.out.print("[" + moves + " moves, score " + score + "] ");
    }

    private static void getCommand() {
        Scanner inputReader = new Scanner(System.in);
        command = inputReader.nextLine();  // command is global.
    }

    private static void navigate() {
        final int INVALID = -1;
        int dir = INVALID;  // This will get set to a value > 0 if a direction command was entered.

        if (        command.equalsIgnoreCase("north")    || command.equalsIgnoreCase("n") ) {
            dir = 0;
        } else if ( command.equalsIgnoreCase("south")    || command.equalsIgnoreCase("s") ) {
            dir = 1;
        } else if ( command.equalsIgnoreCase("east")     || command.equalsIgnoreCase("e") ) {
            dir = 2;
        } else if ( command.equalsIgnoreCase("west")     || command.equalsIgnoreCase("w") ) {
            dir = 3;
        } else if ( command.equalsIgnoreCase("map")      || command.equalsIgnoreCase("m") ) {
            showMap();
        } else if ( command.equalsIgnoreCase("inventory")|| command.equalsIgnoreCase("i") ) {
            showInventory();
        } else if ( command.equalsIgnoreCase("take")     || command.equalsIgnoreCase("t") ) {
            takeItem();
        } else if ( command.equalsIgnoreCase("quit")     || command.equalsIgnoreCase("q") ) {
            quit();
        } else if ( command.equalsIgnoreCase("help")     || command.equalsIgnoreCase("h") ) {
            help();
        };

        if (dir > -1) {   // This means a dir was set.
            int newLocation = nav[currentLocale][dir];
            if (newLocation == INVALID) {
                System.out.println("-----------------------");
                System.out.println("You cannot go that way.");
                System.out.println("-----------------------");
            } else {
                currentLocale = newLocation;
                moves = moves + 1;
                score = score + 5;
                // TODO: Deal with hasVisited and the score here.
                if (Locale[currentLocale].hasVisited = false){
                    score = score + 5;
                }

            }
        }
    }

    private static void help() {
        System.out.println("----------------------------");
        System.out.println("The commands are as follows:");
        System.out.println("   n/north");
        System.out.println("   s/south");
        System.out.println("   e/east");
        System.out.println("   w/west");
        System.out.println("   i/inventory");
        System.out.println("   m/map");
        System.out.println("   t/take");
        System.out.println("   h/help");
        System.out.println("   q/quit");
        System.out.println("----------------------------");
    }

    private static void showMap() {
        System.out.println("The function that displays the map has been called");
    }

    private static void showInventory() {
        System.out.println("The function that displays the inventory has been called");
    }

    private static void takeItem() {
        System.out.println("This function is called when the player add items to their inventory");
    }



    /*private static void pointAdder() {
        //alert("function was called")
        //---Player does not recieve points for entering the Main Rooom e.g. the room they started in---

        if (Locale[currentLocale].hasVisited = false){
            score = score + 5;
        }

        if (currentLocale == 0 && timesRoomEntered.loc0 < 1) {
            //score = score + 5;
            timesRoomEntered.loc0 = timesRoomEntered.loc0 + 1;
        } else if (currentLocale == 1 && timesRoomEntered.loc1 < 1) {
            score = score + 5;
            timesRoomEntered.loc1 = timesRoomEntered.loc1 + 1;
        } else if (currentLocale == 2 && timesRoomEntered.loc2 < 1) {
            score = score + 5;
            timesRoomEntered.loc2 = timesRoomEntered.loc2 + 1;
        } else if (currentLocale == 3 && timesRoomEntered.loc3 < 1) {
            score = score + 5;
            timesRoomEntered.loc3 = timesRoomEntered.loc3 + 1;
        } else if (currentLocale == 4 && timesRoomEntered.loc4 < 1) {
            score = score + 5;
            timesRoomEntered.loc4 = timesRoomEntered.loc4 + 1;
        } else if (currentLocale == 5 && timesRoomEntered.loc5 < 1) {
            score = score + 5;
            timesRoomEntered.loc5 = timesRoomEntered.loc5 + 1;
        } else if (currentLocale == 6 && timesRoomEntered.loc6 < 1) {
            score = score + 5;
            timesRoomEntered.loc6 = timesRoomEntered.loc6 + 1;
        } else if (currentLocale == 7 && timesRoomEntered.loc7 < 1) {
            score = score + 5;
        } else {
            //score = score + 5;
        }
    }*/


    private static void quit() {
        stillPlaying = false;
    }

}