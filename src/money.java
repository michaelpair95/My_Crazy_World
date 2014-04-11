
public class money {

    private String name;
    private int amount=0;
    private int superamt=0;
    private int ATM ;

    public void add(int amount) {
        this.amount= this.amount + amount;
        superamt= superamt + amount;
    }
    public void subtract(int amount){
        this.amount= this.amount - amount;
    }

    public money(String name) {
        this.name = name;
    }
    public money(){
        name=null;
    }

    public int getSuperAmt(){
        return superamt;
    }
    public int getAmt(){
        return amount;
    }

    public String showMoney() {return "Coins:" + this.amount;}

}