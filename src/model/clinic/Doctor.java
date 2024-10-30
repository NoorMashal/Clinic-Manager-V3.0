/**
 * @author Noor Mashal
 */
package model.clinic;

public class Doctor extends Provider {
    private Specialty specialty; // Encapsulated instance variable for specialty. */
    private final String npi; /* Encapsulated instance variable for National Provider Identifier (NPI). */

    /* Constructor for the Doctor class. */
    public Doctor(String fname, String lname, Date dob, Location location, Specialty specialty, String npi) {
        super(fname, lname, dob, location); // Call to Provider class constructor.
        this.specialty = specialty; // Set the doctor's specialty.
        this.npi = npi; // Set the doctor's NPI.
    }

    /* Getter for specialty. */
    public Specialty getSpecialty() {
        return specialty;
    }

    /* Setter for specialty. */
    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    /* Getter for NPI. */
    public String getNpi() {
        return npi;
    }

    /* Override the abstract rate() method to return the doctor's charge rate based on their specialty. */
    @Override
    public int rate() {
        return specialty.getCharge(); // Use the Specialty enum to determine the rate per visit.
    }

    /* toString() method to include doctor-specific details. */
    @Override
    public String toString() {
        return super.toString() + "[" + specialty + ", #" + npi + "]";
    }

    /* equals() method for comparison between doctors. */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; /* Check if both references point to the same object. */
        if (!super.equals(obj)) return false; /* Check equality based on the Provider class. */
        if (obj instanceof Doctor other) { /* Cast to Doctor. */
            return this.specialty.equals(other.specialty) && this.npi.equals(other.npi); // Compare specialty and NPI.
        }
        return false;
    }
}
