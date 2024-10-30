/**
 * @author Noor Mashal
 */
package model.clinic;

public class Patient extends Person {
    private Visit visit; /* Instance variable to track visits. */

    /* Constructor for Test.Patient class */
    public Patient(Profile profile) {
        super(profile.getFirstName(), profile.getLastName(), profile.getDob());

        this.visit = null; /* Initialize the visit linked list */
    }

    /* Getter for visit */
    public Visit getVisit() {
        return visit;
    }

    /* Override the toString method */
    @Override
    public String toString() {
        return super.toString() + ", Visits: " + visit;
    }
    /* Override the equals method */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Patient other = (Patient) obj;
        return this.visit.equals(other.visit);
    }
}
