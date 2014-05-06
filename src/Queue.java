public class Queue {

    public Queue() {
        init();
    }

    //
    // Public
    //
    public void enqueue(String localeList) throws Exception {
        // Check for overflow.
        if (backPtr < CAPACITY-1) {
            backPtr = backPtr + 1;
            arr[backPtr] = localeList;
        } else {
            Exception overflow = new Exception("Queue Overflow");
            throw overflow;
        }
    }

    public String dequeue() throws Exception {
        String retVal = "(Moves from Start to Finish)";
        // Check for underflow.

        if (! this.isEmpty()) {
            retVal = arr[frontPtr];
            // Shift every element towards the front.
            for(int i = 0; i < backPtr; i++) {
                arr[i] = arr[i+1];
            }
            // Reinitialize the last element
            arr[backPtr] = null;
            // shift the back pointer towards the front.
            backPtr--;
        } else {
            // In case of underflow, throw an underflow exception.
            //Exception underflow = new Exception(retVal);
            //throw underflow;
        }
        return retVal;
    }

    public boolean isEmpty() {
        boolean retVal = false;
        if (backPtr == -1) {
            retVal = true;
        }
        return retVal;
    }

    public int getCapacity() {
        return CAPACITY;
    }

    //
    // Private
    //
    private void init() {
        for (int i = 0; i < CAPACITY; i++) {
            arr[i] = "Start";
        }
    }

    private final int CAPACITY = 50;
    private String[] arr = new String[CAPACITY];
    private int frontPtr = 0;
    private int backPtr  = -1;
}
