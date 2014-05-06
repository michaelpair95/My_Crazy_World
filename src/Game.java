/**
 * Created by michaelpair on 2/24/14.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

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
    public static int moves = 0;                // Counter of the player's moves.
    public static int score = 0;                // Tracker of the player's score.
    public static money coins = new money();            // Keeps track of how much money the player has.
    public static Items[] inventory;
    public static ArrayList<String> satchel = new ArrayList<String>();
    public static ListItem purchase = new ListItem();
    public static Scanner transaction = new Scanner(System.in);
    public static  List0 lm1 = new List0();
    public static int num= 5;
    public static boolean visited = false;
    public static Random rand= new Random();
    public static boolean fueled = false;
    public static boolean superGarbage = false;


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


        Locale loc0 = new Locale(0);
        loc0.setName("A Shack");
        loc0.setDesc("It's a pretty small shack, but there appears to be a map tacked to the wall.");
        loc0.setAvailableDirs("North East");

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

        Locale loc4 = new Locale(4);
        loc4.setName("Rainbow Road");
        loc4.setDesc("Wiiiiiiiiiiiiii! But wait, what car do these keys go to?");
        loc4.setAvailableDirs("North East");

        Locale loc5 = new Locale(5);
        loc5.setName("Eetzeek Hagadol");
        loc5.setDesc("Shalom habibi, mah aht rotsah? Would you like some french fries?");
        loc5.setAvailableDirs("West");

        Locale loc6 = new Locale(6);
        loc6.setName("Magick Shoppe");
        loc6.setDesc("Get here, enchanted stuff, you may");
        loc6.setAvailableDirs("North");

        Space loc7 = new Space(7);
        loc7.setName("Kennedy Space Center");
        loc7.setDesc("There's a shuttle ready to launch. To enter, please enter the launch code.");
        loc7.setAvailableDirs("South");
        loc7.setNearestPlanet("Planet Vegeta");

        Time loc8 = new Time(8);
        loc8.setName("The DeLorean");
        loc8.setDesc("The time is set to August 15, 1969"); //TODO: enable the player to put keys in the ignition and put garbage in the mr fusion to power the car
        loc8.setAvailableDirs("South");
        loc8.setNearestYear("August 15, 1969");

        Space loc9 = new Space(9);
        loc9.setName("Planet Vegeta");
        loc9.setDesc("A strange boy with a tail walks up to you holding a strange machine. He tells you to take it.");
        loc9.setAvailableDirs("earth");

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
        loc1.setWest(loc0);
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
        loc9.setSouth(null);
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


    }

    private static void updateDisplay() {
        System.out.println("-------------------------------------------");
        System.out.println(currentLocale.getText());
        System.out.println("[" + moves + " moves, score " + score + "] ");
        System.out.println("-------------------------------------------");
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
        } else if (command.equalsIgnoreCase("turn") && currentLocale == locations[8]) {
            if (fueled == true && inventory[3].itemFound()) {
                endGame();
            } else if (fueled == true && !(inventory[3].itemFound())) {
                System.out.println("You need keys to start a car");
            } else if (inventory[3].itemFound() && fueled == false) {
                System.out.println("The car needs fuel to start, try picking some Super Garbage from the Magick Shoppe");
            } else {
                System.out.println("What makes you think this car will start without the keys or Super Garbage?");
            }
        } else if (command.equalsIgnoreCase("fill") && currentLocale == locations[8]) {
            fuelCar();
        } else if (command.equalsIgnoreCase("atm")) {
            if (inventory[1].itemFound()) {
                useATM();
            } else {
                System.out.println("You don't own that yet.");
            }
        }


        if (      command.equalsIgnoreCase("north") || command.equalsIgnoreCase("n") ) {
            if(currentLocale.getNorth()!=null){
                currentLocale = currentLocale.getNorth();
                moves+=1;
            } else {
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("south") || command.equalsIgnoreCase("s") ) {
            if(currentLocale.getSouth()!=null){
                currentLocale = currentLocale.getSouth();
                moves+=1;

            } else {
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("east")  || command.equalsIgnoreCase("e") ) {
            if(currentLocale.getEast()!=null){
                currentLocale = currentLocale.getEast();
                moves+=1;

            } else {
                System.out.println("You can't go that way");
            }
        } else if ( command.equalsIgnoreCase("west")  || command.equalsIgnoreCase("w") ) {
            if (currentLocale.getWest() != null) {
                currentLocale = currentLocale.getWest();
                moves += 1;

            } else {
                System.out.println("You can't go that way");
            }
        }

        if(currentLocale.getId()==6&& visited==false){
            setupShop(lm1);
        }
        else if(currentLocale.getId()==6){
            transaction();
        }
        currentLocale.numberRoomEnter = currentLocale.numberRoomEnter + 1;
        if (currentLocale.numberRoomEnter <= 1) {
            currentLocale.setHasVisited(false);
            score = score + 5;
        } else if (currentLocale.numberRoomEnter > 1) {
            currentLocale.setHasVisited(true);
        }

        myStack.push(currentLocale.getName());
        try {
            myQueue.enqueue(currentLocale.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void goHome(){
        currentLocale = locations[7];
    }

    private  static  void blastOff() {
        System.out.println("The spacecraft launches and the amount of G-force knocks you unconscious! You awake up on...");
        currentLocale = locations[9];
    }

    private static void useATM() {
        System.out.println("Thank you for using Insta-Cash, we have put more coins in your Satchel");
        coins.add(rand.nextInt((int) (Math.random()*100)));
    }


    private static void help() {
        System.out.println("-------------------------------------------");;
        System.out.println("The commands are as follows:");
        System.out.println("   n/north");
        System.out.println("   s/south");
        System.out.println("   e/east");
        System.out.println("   w/west");
    if (currentLocale == locations[9]) {
        System.out.println("   earth (takes you back to earth");
    }
    if (inventory[1].itemFound()) {
        System.out.println("   atm (pulls out the Insta-Cash");
    }
        System.out.println("   i/inventory");
        System.out.println("   m/map");
        System.out.println("   t/take");
        System.out.println("   h/help");
        System.out.println("   q/quit");
        System.out.println("-------------------------------------------");
    }


    private static void showInventory(){
        System.out.println(satchel + "\n" + coins.showMoney());
    }

    private static void takeItem() {

        if (currentLocale == locations[0]) {
            inventory[0].setFound(true);
            satchel.add(inventory[0].getName());
            System.out.println("The " + inventory[0].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("It's a pretty small shack");
        }
        if (currentLocale == locations[9]) {
            inventory[1].setFound(true);
            satchel.add(inventory[1].getName());
            System.out.println("The " + inventory[1].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("The strange boy with the tail is seen running away in the distance");
        }
        if (currentLocale == locations[3]) {
            inventory[2].setFound(true);
            satchel.add(inventory[2].getName());
            System.out.println("The " + inventory[2].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("We have every brand you can think of!");
        }
        if (currentLocale == locations[4]) {
            inventory[3].setFound(true);
            satchel.add(inventory[3].getName());
            System.out.println("The " + inventory[3].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("Wiiiiiiiiiiiiii!");
        }
        if (currentLocale == locations[2]) {
            inventory[4].setFound(true);
            satchel.add(inventory[4].getName());
            System.out.println("The " + inventory[4].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("It's so boring in here...");
        }
        if (currentLocale == locations[5]) {
            inventory[5].setFound(true);
            satchel.add(inventory[5].getName());
            System.out.println("The " + inventory[5].getName() + " was placed in your handy dandy belt Satchel!");
            currentLocale.setDesc("Shalom habibi, mah aht rotsah?");
        }

        else if (currentLocale == locations[6] || currentLocale == locations[7] || currentLocale == locations[8]) {
            System.out.println("There's nothing to take!");
        }
    }

    private static void quit() {
        stillPlaying = false;
        System.out.println("Would you like to see your moves from the start, finish, or neither?");
        getCommand();
        if(command.equalsIgnoreCase("start")){
            movesForward();
        } else if (command.equalsIgnoreCase("finish")){
            movesBackwards();
        }
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

    private static void endGame() {
        System.out.println("WELCOME TO WOODSTOCK!!!!!!!!!");
        System.out.println("YOU WON!!!!!!!");
        System.out.println("Would you like to see your moves from the start, finish, or neither?");
        getCommand();
        if(command.equalsIgnoreCase("start")){
            movesForward();
        } else if (command.equalsIgnoreCase("finish")){
            movesBackwards();
        }
    }

    private static void fuelCar() {
        if (superGarbage == true) {
            fueled =  true;
            System.out.println("You put fuel in the car.");
        } else {
            System.out.println("You don't have any fuel, you need super garbage!");
        }
    }


    private static boolean sequentialSearch(List0 lm,
                                            String target) {

        System.out.println("Let me look for " + target + ".");
        int counter = 0;
        ListItem currentItem = new ListItem();
        currentItem = lm.getHead();
        boolean isFound = false;
        while ( (!isFound) && (currentItem != null) ) {
            counter = counter +1;
            if (currentItem.getName().equalsIgnoreCase(target)) {
                //We found it!
                isFound = true;

            } else {
                // Keep looking.
                currentItem = currentItem.getNext();
            }
        }
        if (isFound) {
            System.out.println("Hey, here's the "+ target +".");
            System.out.println("It'll cost you "+ currentItem.getCost());
            System.out.println("Do you want to purchase this item?");
            String buy = transaction.nextLine();
            if(coins.getAmt()>=currentItem.getCost()&&buy.equalsIgnoreCase("yes")){
                purchase=currentItem;
                return true;
            }
            else if(buy.equalsIgnoreCase("yes")){
                System.out.println("You don't have enough coins to buy that.");
                return false;
            }
            else{
                return false;
            }

        } else {
            System.out.println("I don't seem to see a " + target + "We might not have it, but could you try to describe it again?");
            return false;
        }
    }
    public static void transaction(){
        System.out.println("What would you like?");
        String selection=transaction.nextLine();
        if(sequentialSearch(lm1, selection)== true){
            coins.subtract(purchase.getCost());
            Items magicItem=new Items(num, purchase.getName(), purchase.getDesc(), true);
            satchel.add(magicItem.getName());
            if (magicItem.getName().equals("Super Garbage")) {
                superGarbage = true;
            }
        }
    }
    private static void readMagicItemsFromFile(String fileName,
                                               List0 lm) {
        File myFile = new File(fileName);
        try {
            Scanner input = new Scanner(myFile);
            while (input.hasNext()) {
        // Read a line from the file.
                String itemName = input.nextLine();

        // Construct a new list item and set its attributes.
                ListItem fileItem = new ListItem();
                fileItem.setName(itemName);
                fileItem.setDesc(" ");
                fileItem.setCost((int) (Math.random() * 100));
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
    private static void setupShop(List0 lm){
        System.out.println("I need to finish setting up, come back later");
        readMagicItemsFromFile("magicitems.txt", lm);
        visited=true;
        currentLocale = locations[1];

    }

    static Stack myStack = new Stack();
    //Create the stack for the locations; Backwards
    static Queue myQueue = new Queue();
    //Create the queue for the locations; Forwards


    private static void movesBackwards() {

        for(int i = 0; i <= moves + 1 ; i++){
            try {
                System.out.println(myStack.pop());

            } catch (Exception ex) {
                System.out.println("Caught exception: " + ex.getMessage());
            }
        }
    }

    private static void movesForward() {

        for(int i = 0; i <= moves + 1 ; i++){
            try {
                System.out.println(myQueue.dequeue());

            } catch (Exception ex) {
                System.out.println("Caught exception: " + ex.getMessage());
            }
        }
    }


}