/**
 * @author Noor Mashal
 */

package model.clinic;

public class Person implements Comparable<Person> {
    protected Profile profile; /* Instance variable for profile */

    /* Constructor */
    public Person(String fname, String lname, Date dob) {
        this.profile = Profile.createProfile(fname, lname, dob); // Create profile using the static method
    }

    /* Getter for profile */
    public Profile getProfile() {
        return profile;
    }

    /* Override toString method */
    @Override
    public String toString() {
        return "" + profile;
    }

    /* Override equals method */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check reference equality
        if (!(obj instanceof Person)) return false; // Check type
        Person other = (Person) obj; // Cast
        return profile.equals(other.profile); // Compare profiles
    }

    /* Implementing the compareTo method */
    @Override
    public int compareTo(Person other) {
        return this.profile.getLastName().compareTo(other.profile.getLastName()); // Example comparison based on last name
    }
}
