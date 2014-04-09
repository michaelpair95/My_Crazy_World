public class ListItem {

    //
    // Public
    //

    // Constructor
    public ListItem(){
    }

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ListItem getNext() {
        return next;
    }
    public void setNext(ListItem next) {
        this.next = next;
    }

    public int getCost() {return cost;}
    public void setCost(int cost) {this.cost = cost;}

    // Other methods
    @Override
    public String toString() {
        return this.name;
    }


    //
    // Private
    //
    private String name;
    private String desc;
    private int cost;
    private ListItem next = null;


}