/**
 * Enum representing different clinic locations with corresponding ZIP codes and counties.
 * @author Noor Mashal
 */
package model.clinic;

/* Enum class to define various locations for the clinic */
public enum Location {

    /* Predefined locations with ZIP code and county */
    BRIDGEWATER("08807", "Somerset"),
    EDISON("08817", "Middlesex"),
    PISCATAWAY("08854", "Middlesex"),
    PRINCETON("08542", "Mercer"),
    MORRISTOWN("07960", "Morris"),
    CLARK("07066", "Union");

    private final String zip;
    private final String county;

    /**
     * Constructor for the enum values
     * Initializes the zip and county fields for each location
     */
    private Location(String zip, String county) {
        this.zip = zip;
        this.county = county;
    }

    /* Getter method to return the county of the location */
    public String getCounty() {
        return this.county;
    }
    public String getZip() {
        return this.zip;
    }
    /**
     * Override method for toString()
     * */
    @Override
    public String toString() {
        return String.format("%s, %s %s", name(), county, zip);
    }
}
