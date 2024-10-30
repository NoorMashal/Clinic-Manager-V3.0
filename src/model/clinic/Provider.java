/**
 * @author Noor Mashal
 */
package model.clinic;

public abstract class Provider extends Person {
    private Location location;

    /* Constructor for the Provider class. */
    public Provider(String fname, String lname, Date dob, Location location) {
        super(fname, lname, dob); // Call to Person class constructor.
        this.location = location; // Set location for this provider.
    }

    /* Abstract method to define the charging rate per visit. */
    public abstract int rate();

    /* Getter for the provider's location. */
    public Location getLocation() {
        return location;
    }

    /* Setter for the provider's location. */
    public void setLocation(Location location) {
        this.location = location;
    }

    /* toString method to include provider details. */
    @Override
    public String toString() {
        return super.toString() + ", " + location + "]";
    }

    /* equals method for provider comparison. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if both references point to the same object.
        if (!super.equals(obj)) return false; // Check equality based on the Person class.
        if (obj instanceof Provider) { // Ensure the object is an instance of Provider.
            Provider other = (Provider) obj; // Cast to Provider.
            return this.location.equals(other.location); // Compare locations.
        }
        return false;
    }
}
