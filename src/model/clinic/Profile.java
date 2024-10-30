/**
 * @author Noor Mashal
 */

package model.clinic;

public class Profile implements Comparable<Profile> {
    private String fname; /* First Name */
    private String lname; /* Last Name */
    private Date dob; /* Test.Date of birth */

    private Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /* create an instance */
    public static Profile createProfile(String fname, String lname, Date dob) {
        return new Profile(fname, lname, dob);
    }
    public String getFirstName() {
        return fname;
    }

    public String getLastName() {
        return lname;
    }
    public String getFullName() { return fname + " " +lname; }
    public Date getDob() {
        return dob;
    }

    /**
     * Override method for compareTo() | equals()
     * */
    @Override
    public int compareTo(Profile other) {

        /* Compare last names character by character */
        for (int i = 0; i < this.lname.length() && i < other.lname.length(); i++) {
            if (this.lname.charAt(i) != other.lname.charAt(i)) {
                return this.lname.charAt(i) - other.lname.charAt(i);
            }
        }
        /* Compare first names character by character */
        for (int i = 0; i < this.fname.length() && i < other.fname.length(); i++) {
            if (this.fname.charAt(i) != other.fname.charAt(i)) {
                return this.fname.charAt(i) - other.fname.charAt(i);
            }
        }

        String thisDob = this.dob.toString();
        String otherDob = other.dob.toString();

        return thisDob.compareTo(otherDob); /* Compare by formatted date of birth */
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Profile profile = (Profile) obj;
        return fname.equals(profile.fname) &&
                lname.equals(profile.lname) &&
                dob.equals(profile.dob);
    }

    // toString method to print the profile details
    public String toString() {
        return this.displayProfile();
    }
    private String displayProfile() {
//        String dobFormatted = formatDOB(dob);
        return fname + ' ' + lname + ' ' + dob;
    }
}
