/**
 * @author Noor Mashal
 */

package model.clinic;

public class Imaging extends Appointment {
    private Radiology room; // Instance variable for the imaging service

    /* Constructor */
    public Imaging(Date date, Timeslot timeslot, String patientFirstName, String patientLastName, String patientDOB,Technician technician, Radiology room) {
        super(date, timeslot, Profile.createProfile(patientFirstName, patientLastName, stringToDate(patientDOB)), technician);


        /* Assign the imaging service (room) to the instance variable */
        this.room = room;
    }

    /* Method to convert String to Date */
    public static Date stringToDate(String stringDate) {
        String[] dateParts = stringDate.split("/"); /* format "MM/DD/YYYY" */
        return new Date(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1])); /* Create a Date object using parsed values */
    }

    /* Getter for the imaging service */
    public Radiology getRoom() {
        return room;
    }

    /* Override the toString() method */
    @Override
    public String toString() {
        return super.toString() + "[" + room + "]"; // Append imaging service information to the appointment details
    }

    /* Override the equals method */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Imaging)) return false;
        Imaging other = (Imaging) obj;
        return super.equals(obj) && this.room.equals(other.room); // Compare based on parent attributes and imaging service
    }
}
