public class Locale {

    private Locale next;
    //
    // Public
    //
    public int numberRoomEnter;

    // Constructors
    public Locale(){
        this.id = 0;
    }
    public Locale(int id) {
        this.id = id;
    }
    public Locale(int id, Locale north, Locale south, Locale east, Locale west){
        this.id = id;
        this.north=north;
        this.south=south;
        this.east=east;
        this.west=west;
    }

    // Getters and Setters
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id=id;
    }

    public Locale getNorth(){
        return north;
    }
    public void setNorth(Locale north){
        this.north=north;
    }

    public Locale getSouth(){
        return south;
    }
    public void setSouth(Locale south){
        this.south=south;
    }

    public Locale getEast(){
        return east;
    }
    public void setEast(Locale east){
        this.east=east;
    }

    public Locale getWest(){
        return west;
    }
    public void setWest(Locale west){
        this.west=west;
    }

    public String getAvailableDirs() {
        return availableDirs;
    }
    public void setAvailableDirs(String value) {
        this.availableDirs = value;
    }

    public int getNumberRoomEnter() {
        return numberRoomEnter;
    }
    public void setNumberRoomEnter(int numberRoomEnter) {
        this.numberRoomEnter = numberRoomEnter;
    }

    public String getText() {
        return this.name + "\n" + this.desc +"\n You can go "+ this.availableDirs;
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

    public boolean getHasVisited() {
        return hasVisited;
    }
    public void setHasVisited(boolean hasVisited) {
        this.hasVisited = hasVisited;
    }

    public Locale getNext() {
        return next;
    }
    public void setNext(Locale next) {
        this.next = next;
    }



    //
    //  Private
    //
    private int     id;
    private String  name;
    private String  desc;
    private String  availableDirs; // The directions where the player can go from this locale.
    private boolean hasVisited = false;
    private Locale  north, south, east, west;
}
