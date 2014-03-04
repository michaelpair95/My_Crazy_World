/**
 * Created by michaelpair on 3/4/14.
 */
public class Time extends Locale {

    //
    // -- PRIVATE --
    //

    // Constructor
    public Time(int id) {
        super(id);
    }

    // Getters and Setters
    public void setNearestYear(String value) {
        this.nearestYear = value;
    }
    public String getNearestYear() {
        return this.nearestYear;
    }

    // Other methods
    @Override
    public String toString() {
        return "Space..." + super.toString() + " nearestPlanet=" + this.nearestYear;
    }


    //
    // -- PRIVATE --
    //
    private String nearestYear;

}