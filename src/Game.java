/**
 * Created by michaelpair on 2/24/14.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {

    //
    // Public
    //

    // Globals
    public static final boolean DEBUGGING  = false; // Debugging flag.
    public static final int MAX_LOCALES = 10;    // Total number of rooms/locations we have in the game.
    public static Locale currentLocale = null;        // Player starts in locale 0.
    public static String command;               // What the player types as he or she plays the game.
    public static boolean stillPlaying = true;  // Controls the game loop.
    public static Locale[] locations;           // An uninitialized array of type Locale. See init() for initialization.
    public static int[][]  nav;                 // An uninitialized array of type int int.
    public static int moves = 0;                // Counter of the player's moves.
    public static int score = 0;                // Tracker of the player's score.
    public static int money = 1000;                // Keeps track of how much money the player has.
    public static Items[] inventory;
    public static String purchasedItem;         // this is where your purchased items are held
    public static ListItem purchase = new ListItem();
    //public static ArrayList<string>

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
                    currentLocale = locations[0];
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
        System.out.println("To see a list of commands, type \"h\" or \"help\"");
        System.out.println("You have just woken up in a crazy futuristic society and you have to get home!");


        // Set up the location instances of the Locale class.
        // The unused code is there because I was testing something, and couldn't seem to get it to work
        // but I still want to try it out later
        Locale loc0 = new Locale(0);
        loc0.setName("A Shack");
        loc0.setDesc("It's a pretty small shack, but there appears to be a map tacked to the wall.");
        loc0.setAvailableDirs("North East");
        //loc0.setHasVisited(false);

        Locale loc1 = new Locale(1);
        loc1.setName("Erebor");
        loc1.setDesc("What happened to all the gold and why are there so many mushrooms all over the place. \n They look pretty easy to take from the ground");
        loc1.setAvailableDirs("North South West");
        //loc1.setHasVisited(false);

        Locale loc2 = new Locale(2);
        loc2.setName("The Library");
        loc2.setDesc("It's so boring here... wait, are those launch codes on the table?! Those look fun!");
        loc2.setAvailableDirs("North South East West");
        //loc2.setHasVisited(false);

        Locale loc3 = new Locale(3);
        loc3.setName("The SUPER Market");
        loc3.setDesc("We have every brand you can think of! Here take this free sample.");
        loc3.setAvailableDirs("South West East");
        //loc3.setHasVisited(false);

        Locale loc4 = new Locale(4);
        loc4.setName("Rainbow Road");
        loc4.setDesc("Wiiiiiiiiiiiiii! But wait, what car do these keys go to?");
        loc4.setAvailableDirs("North East");
        //loc4.setHasVisited(false);

        Locale loc5 = new Locale(5);
        loc5.setName("Eetzeek Hagadol");
        loc5.setDesc("Shalom habibi, mah aht rotsah? Would you like some french fries?");
        loc5.setAvailableDirs("West");
        //loc5.setHasVisited(false);

        Locale loc6 = new Locale(6);
        loc6.setName("Magick Shoppe");
        loc6.setDesc("Get here, enchanted stuff, you may");
        loc6.setAvailableDirs("North");
        //loc6.setHasVisited(false);

        Space loc7 = new Space(7);
        loc7.setName("Kennedy Space Center");
        loc7.setDesc("There's a shuttle ready to launch. To enter, please enter the launch code.");
        loc7.setAvailableDirs("South");
        loc7.setNearestPlanet("Planet Vegeta");
        //loc7.setHasVisited(false);

        Time loc8 = new Time(8);
        loc8.setName("The DeLorean");
        loc8.setDesc("The time is set to August 15, 1969"); //TODO: enable the player to put keys in the ignition and put garbage in the mr fusion to power the car
        loc8.setAvailableDirs("South");
        loc8.setNearestYear("August 15, 1969");
        //loc8.setHasVisited(false);

        Space loc9 = new Space(9);
        loc9.setName("Planet Vegeta");
        loc9.setDesc("A strange boy with a tail walks up to you holding a strange machine. He tells you to take it.");
        //loc9.setAvailableDirs("");

        // Set up the location array.
        locations = new Locale[10];
        locations[0] = loc0;
        locations[1] = loc1;
        locations[2] = loc2;
        locations[3] = loc3;
        locations[4] = loc4;
        locations[5] = loc5;
        locations[6] = loc6;
        locations[7] = loc7;
        locations[8] = loc8;
        locations[9] = loc9;

        // Linking up the Locales
        loc0.setNorth(loc3);
        loc0.setSouth(null);
        loc0.setEast(loc1);
        loc0.setWest(null);
        loc1.setNorth(loc2);
        loc1.setSouth(loc6);
        loc1.setEast(null);
        loc1.setWest(loc1);
        loc2.setNorth(loc7);
        loc2.setSouth(loc1);
        loc2.setEast(loc5);
        loc2.setWest(loc3);
        loc3.setNorth(null);
        loc3.setSouth(loc0);
        loc3.setEast(loc2);
        loc3.setWest(loc4);
        loc4.setNorth(loc8);
        loc4.setSouth(null);
        loc4.setEast(loc3);
        loc4.setWest(null);
        loc5.setNorth(null);
        loc5.setSouth(null);
        loc5.setEast(null);
        loc5.setWest(loc2);
        loc6.setNorth(loc1);
        loc6.setSouth(null);
        loc6.setEast(null);
        loc6.setWest(null);
        loc7.setNorth(null);
        loc7.setSouth(loc2);
        loc7.setEast(null);
        loc7.setWest(null);
        loc8.setNorth(null);
        loc8.setSouth(loc4);
        loc8.setEast(null);
        loc8.setWest(null);
        loc9.setNorth(null);
        loc9.setSouth(loc7);
        loc9.setEast(null);
        loc9.setWest(null);



        Items item0 = new Items(0);
        item0.setName("Map");
        item0.setDesc("Looks like a map of the area");

        Items item1 = new Items(1);
        item1.setName("Insta-Cash");
        item1.setDesc("A portable ATM that gives out the gold that was in Erebor.");

        Items item2 = new Items(2);
        item2.setName("Mushrooms");
        item2.setDesc("It's got a red and white top and a face. WHAT?!");

        Items item3 = new Items(3);
        item3.setName("Car Keys");
        item3.setDesc("The key fob says DMC on it");

        Items item4 = new Items(4);
        item4.setName("Launch Codes");
        item4.setDesc("8490126");

        Items item5 = new Items(5);
        item5.setName("French Fries");
        item5.setDesc("They have the special frie salt! OH MY GOD!!!!");



        inventory  = new Items[6];
        inventory[0] = item0;
        inventory[1] = item1;
        inventory[2] = item2;
        inventory[3] = item3;
        inventory[4] = item4;
        inventory[5] = item5;

        currentLocale=loc0;


        /*

        // Set up the navigation matrix.
        nav = new int[][] {
        N   S   E  W
        0   1   2  3
        { 3, -1,  1, -1},
        { 2,  6, -1,  0},
        { 7,  1,  5,  3},
        {-1,  0,  2,  4},
        { 8, -1,  3, -1},
        {-1, -1, -1,  2},
        { 1, -1, -1, -1},
        {-1,  2, -1, -1},
        {-1,  4, -1, -1},
        };
        */
    }

    private static void updateDisplay() {
        System.out.println("-------------------------------------------");
        System.out.println(currentLocale.getText());
        System.out.println("[" + moves + " moves, score " + score + "] ");
        System.out.println("-------------------------------------------");
        if (currentLocale == locations[6]){
            createMagicItems();
        }
    }

    private static void getCommand() {
        Scanner inputReader = new Scanner(System.in);
        command = inputReader.nextLine();  // command is global.
    }

    private static void navigate() {




        if (command.equalsIgnoreCase("map") || command.equalsIgnoreCase("m")) {
            Map();
        } else if (command.equalsIgnoreCase("inventory") || command.equalsIgnoreCase("i")) {
            showInventory();
        } else if (command.equalsIgnoreCase("take") || command.equalsIgnoreCase("t")) {
            takeItem();
        } else if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("q")) {
            quit();
        } else if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("h")) {
            help();
        } else if (command.equalsIgnoreCase("8490126") && currentLocale == locations[7]) {
            blastOff();
        } else if (command.equalsIgnoreCase("earth") && currentLocale == locations[9]) {
            goHome();
        }


        if (      command.equalsIgnoreCase("north") || command.equalsIgnoreCase("n") ) {
            if(currentLocale.getNorth()!=null){
                currentLocale = currentLocale.getNorth();
                moves+=1;
                score+=5;

            } else {
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("south") || command.equalsIgnoreCase("s") ) {
            if(currentLocale.getSouth()!=null){
                currentLocale = currentLocale.getSouth();
                moves+=1;
                score+=5;

            } else {
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("east")  || command.equalsIgnoreCase("e") ) {
            if(currentLocale.getEast()!=null){
                currentLocale = currentLocale.getEast();
                moves+=1;
                score+=5;

            } else {
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("west")  || command.equalsIgnoreCase("w") ) {
            if (currentLocale.getWest() != null) {
                currentLocale = currentLocale.getWest();
                moves += 1;
                score+=5;

            } else {
                System.out.println("You can't go that way");
            }
        }
    }

    private static void goHome(){
        currentLocale = locations[7];
    }

    private  static  void blastOff() {
        System.out.println("The spacecraft launches and the amount of G-force knocks you unconscious! You awake up on...");
        currentLocale = locations[9];
    }



    private static void help() {
        System.out.println("-------------------------------------------");;
        System.out.println("The commands are as follows:");
        System.out.println("   n/north");
        System.out.println("   s/south");
        System.out.println("   e/east");
        System.out.println("   w/west");
        System.out.println("   l/leave (for the Magick Shoppe.");
        System.out.println("   earth (takes you back to earth");
        System.out.println("   i/inventory");
        System.out.println("   m/map");
        System.out.println("   t/take");
        System.out.println("   h/help");
        System.out.println("   q/quit");
        System.out.println("-------------------------------------------");
    }


    private static void showInventory(){

        String satchel="";
        if (inventory[0].itemFound()){
            satchel= satchel+inventory[0].toString()+ "\n";
        }
        if(inventory[1].itemFound()){
            satchel =satchel+inventory[1].toString()+ "\n";
        }
        if(inventory[2].itemFound()){
            satchel =satchel+inventory[2].toString()+ "\n";
        }
        if(inventory[3].itemFound()){
            satchel =satchel+inventory[3].toString()+ "\n";
        }
        if(inventory[4].itemFound()){
            satchel =satchel+inventory[4].toString()+ "\n";
        }
        if(inventory[5].itemFound()){
            satchel =satchel+inventory[4].toString()+ "\n";
        }
        System.out.println(satchel);

    }

    private static void takeItem(){

        if (currentLocale == locations[0]) {
            inventory[0].setFound(true);
            System.out.println("The " + inventory[0].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("It's a pretty small shack");
        }
        if (currentLocale == locations[9]) {
            inventory[1].setFound(true);
            System.out.println("The " + inventory[1].getName() + " was placed in your handy dandy belt Satchel!");
        }
        if (currentLocale == locations[3]) {
            inventory[2].setFound(true);
            System.out.println("The " + inventory[2].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("We have every brand you can think of!");
        }
        if (currentLocale == locations[4]) {
            inventory[3].setFound(true);
            System.out.println("The " + inventory[3].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("Wiiiiiiiiiiiiii!");
        }
        if (currentLocale == locations[2]) {
            inventory[4].setFound(true);
            System.out.println("The " + inventory[4].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("It's so boring in here...");
        }
        if (currentLocale == locations[5]) {
            inventory[5].setFound(true);
            System.out.println("The " + inventory[5].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("Shalom habibi, mah aht rotsah?");
        }

        else if (currentLocale == locations[6] || currentLocale == locations[7] || currentLocale == locations[8]) {
            System.out.println("There's nothing to take!");
        }
    }

    private static void quit() {
        stillPlaying = false;
    }

    private static void Map() {

        if (inventory[0].itemFound()){
            System.out.println("++++++++++++++++++                  +++++Kennedy+++++");
            System.out.println("+++The DeLorean+++                  ++++++Space++++++");
            System.out.println("++++++++++++++++++                  ++++++Center+++++");
            System.out.println("       ||                                   ||");
            System.out.println("       ||                                   ||");
            System.out.println("++++++++++++++++++   +++++The+++++   +++++++++++++++++   +++Etzeek++++");
            System.out.println("+++Rainbow Road+++===++++SUPER++++===+++The Library+++===+++++++++++++");
            System.out.println("++++++++++++++++++   ++++Market+++   +++++++++++++++++   +++Hagadol+++");
            System.out.println("                          ||                ||");
            System.out.println("                          ||                ||");
            System.out.println("                     +++++++++++++   +++++++++++++++++");
            System.out.println("                     +++A Shack+++===++++++Erebor+++++");
            System.out.println("                     +++++++++++++   +++++++++++++++++");
            System.out.println("                                            ||");
            System.out.println("                                            ||");
            System.out.println("                                     ++++++Magick+++++");
            System.out.println("                                     ++++++Shoppe+++++");

        }
        else {
            System.out.println("What map?");
        }
    }


    private static void giveMoney() {

    }

    private static void createMagicItems() {
            // Make the list manager.
            ListMan lm1 = new ListMan();
            lm1.setName("Magic Items");
            lm1.setDesc("These are some of my favorite things.");

            final String fileName = "magicitems.txt";

            readMagicItemsFromFileToList(fileName, lm1);

            // Declare an array for the items.
            ListItem[] items = new ListItem[lm1.getLength()];
            readMagicItemsFromFileToArray(fileName, items);

            // Ask player for an item.
            Scanner inputReader = new Scanner(System.in);
            System.out.print("What item would you like? ");
            String targetItem = new String();
            targetItem = inputReader.nextLine();
            System.out.println();

            ListItem li = new ListItem();
            li = sequentialSearch(lm1, targetItem);
            if (li != null) {
                System.out.println(li.toString());
            }
        }


    private static ListItem sequentialSearch(ListMan lm,
                                             String target) {
        ListItem retVal = null;
        System.out.println("Let me see if we have a " + target + ".");
        int counter = 0;
        ListItem currentItem = new ListItem();
        currentItem = lm.getHead();
        boolean isFound = false;
        while ( (!isFound) && (currentItem != null) ) {
            counter = counter +1;
            if (currentItem.getName().equalsIgnoreCase(target)) {
                // We found it!
                isFound = true;
                retVal = currentItem;
            } else {
                // Keep looking.
                currentItem = currentItem.getNext();
            }
        }
        if (isFound) {
            System.out.println("We have a " + retVal + ". It wil cost you " + retVal.getCost() ); //TODO: setup pricing system
                if (money >= retVal.getCost()) {

                    purchase = retVal;
                    //Items magicItem = new Items(purschase.getName(), retVal.getDesc());
                    //satchel.add(magicItem.getName());
                }
                else if (money < retVal.getCost()) {
                    System.out.println("I'm sorry, but you do not have enough money.");
                }
        } else {
            System.out.println("I'm sorry, but we don't have that could you be more specific or would you like to look for something else?");
        }

        return null;
    }

    //private static void purchaseItem(){
      //  Items magicItem = new Items(purchase.getName())
    //}


    private static void readMagicItemsFromFileToList(String fileName,
                                                     ListMan lm) {
        File myFile = new File(fileName);
        try {
            Scanner input = new Scanner(myFile);
            while (input.hasNext()) {
                // Read a line from the file.
                String itemName = input.nextLine();

                // Construct a new list item and set its attributes.
                ListItem fileItem = new ListItem();
                fileItem.setName(itemName);
                fileItem.setCost(Math.random() * 100);
                fileItem.setNext(null); // Still redundant. Still safe.

                // Add the newly constructed item to the list.
                lm.add(fileItem);
            }
            // Close the file.
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. " + ex.toString());
        }

    }

    private static void readMagicItemsFromFileToArray(String fileName,
                                                      ListItem[] items) {
        File myFile = new File(fileName);
        try {
            int itemCount = 0;
            Scanner input = new Scanner(myFile);

            while (input.hasNext() && itemCount < items.length) {
                // Read a line from the file.
                String itemName = input.nextLine();

                // Construct a new list item and set its attributes.
                ListItem fileItem = new ListItem();
                fileItem.setName(itemName);
                fileItem.setCost(Math.random() * 100);
                fileItem.setNext(null); // Still redundant. Still safe.

                // Add the newly constructed item to the array.
                items[itemCount] = fileItem;
                itemCount = itemCount + 1;
            }
            // Close the file.
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. " + ex.toString());
        }
    }

    private static void selectionSort(ListItem[] items) {
        for (int pass = 0; pass < items.length-1; pass++) {
            // System.out.println(pass + "-" + items[pass]);
            int indexOfTarget = pass;
            int indexOfSmallest = indexOfTarget;
            for (int j = indexOfTarget+1; j < items.length; j++) {
                if (items[j].getName().compareToIgnoreCase(items[indexOfSmallest].getName()) < 0) {
                    indexOfSmallest = j;
                }
            }
            ListItem temp = items[indexOfTarget];
            items[indexOfTarget] = items[indexOfSmallest];
            items[indexOfSmallest] = temp;
        }
    }






}