/**
 * @author Noor Mashal
 */
package model.clinic;

public class Technician extends Provider {
    private int ratePerVisit; /* Instance variable to store the rate per visit for the technician */

    /* Constructor for the Technician class */
    public Technician(String fname, String lname, Date dob, Location location, int ratePerVisit) {
        super(fname, lname, dob, location); /* Call to the super class */
        this.ratePerVisit = ratePerVisit; /* Set the rate for the technician */
    }

    /* Returns rate */
    @Override
    public int rate() {
        return ratePerVisit; /* Return the technician's rate per visit */
    }

    /* toString method to include technician details */
    @Override
    public String toString() {
        return super.toString() + "[rate: $" + String.format("%.2f", (double) ratePerVisit) + "]";
    }

    /* equals() method for comparisons */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false; /* Check equality based on the Provider class */
        if (obj instanceof Technician) { /* Ensure the object is an instance of Technician */
            Technician other = (Technician) obj; /* Cast to Technician */
            return this.ratePerVisit == other.ratePerVisit; /* Compare rates */
        }
        return false;
    }
}
