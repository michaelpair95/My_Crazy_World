public class List0 {

    //
    // Public
    //
    public List0() {
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name; }

    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc = desc;}

    public int getLength() {return this.length;}

    public ListItem getHead() {return head;}
    public void setHead(ListItem head) {this.head = head;}

    public ListItem getLast() {return last;}
    public void setLast(ListItem last) {this.last = last;}

    public void add(ListItem item) {
        // System.out.println("adding " + item.toString());
        if (this.head == null) {
            // The list is empty.
            this.head = item;
            this.last = item;
        } else {
            // The list is NOT empty.
            this.last.setNext(item);
            this.last = item;
        }
        this.length = this.length + 1;
    }

    // Other methods
    @Override
    public String toString() {
        String retVal = new String();
        retVal = " name=" + this.name + " desc=" + this.desc + "\n";
        ListItem currentItem = this.head;
        while (currentItem != null) {
            retVal = retVal + currentItem.toString() + "\n";
            currentItem = currentItem.getNext();
        }
        return retVal;
    }

    //
    // Private
    //
    private String name;
    private String desc;
    private int length = 0;
    private ListItem head = null;
    private ListItem last = null;
}