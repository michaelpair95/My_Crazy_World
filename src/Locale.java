/**
 * Created by michaelpair on 2/24/14.
 */
public class Locale {

    //
    // Public
    //
    public boolean hasVisited;
    public int numberRoomEnter;
    public static final boolean DEBUGGING  = false; // Debugging flag.

    // Constructor
    public Locale(int id) {
        this.id = id;
    }

    // Getters and Setters
    public int getId() {
        return this.id;
    }

    public String getText() {
        if (DEBUGGING) {
        return   "            Location: " + this.name          +
               "\n         Description: " + this.desc          +
               "\nAvailable Directions: " + this.availableDirs +
               "\n         Has visited: " + this.hasVisited    +
               "\n      # room visited: " + this.numberRoomEnter;
        }
        else {
            return this.name + "\n" + this.desc + "\nAvailable Directions: " + this.availableDirs;
        }
    }

    public String getName() {
        return this.name;
    }
    public void setName(String value) {
        this.name = value;
    }

    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String value) {
        this.desc = value;
    }

    public int getNumberRoomEnter() {return numberRoomEnter;}
    public void setNumberRoomEnter(int numberRoomEnter) {this.numberRoomEnter = numberRoomEnter;}

    public boolean getHasVisited() {
        return hasVisited;
    }
    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }

    public String getAvailableDirs() {return availableDirs;}
    public void setAvailableDirs(String value) {this.availableDirs = value;}

    // Other methods
   /* @Override
    public String toString(){
        return "[Locale id="
                + this.id
                + " name="
                + this.name
                + " desc=" + this.desc
                + " hasVisited=" + this.hasVisited + "]";
    } */

    //
    //  Private
    //
    private int    id;
    private String name;
    private String desc;
    private String availableDirs;
}