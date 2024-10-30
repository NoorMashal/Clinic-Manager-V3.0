/**
 * @author Noor Mashal
 */
package model.clinic;

public class Appointment implements Comparable<Appointment> {
    /* Private fields representing the appointment details */
    protected Date date;
    protected Timeslot timeslot;
    protected Profile patient;
    protected Provider provider;
    /**
     * Constructor to initialize an Appointment object.
     *
     * @param date      The date of the appointment
     * @param timeslot  The time slot of the appointment
     * @param patient   The patient involved in the appointment
     * @param provider  The provider/doctor for the appointment
     */
    public Appointment(Date date, Timeslot timeslot, Profile patient, Provider provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }
    /* Getter Methods */
    public Date getDate() {
        return date;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Profile getPatient() {
        return patient;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setTimeslot(Timeslot newTimeslot) { this.timeslot = newTimeslot; }

    /**
     * Override method for compareTo(), used to compare two Appointment objects.
     * It first compares by date, then by timeslot, and finally by patient.
     */
    @Override
    public int compareTo(Appointment other) {
        int dateCompare = date.compareTo(other.date);
        if (dateCompare != 0) return dateCompare;
        int timeslotCompare = this.timeslot.compareTo(other.timeslot);
        if (timeslotCompare != 0) return timeslotCompare;

        return this.patient.compareTo(other.patient);
    }
    /**
     * Override method for equals(), used to check if two Appointment objects are equal.
     * Appointments are considered equal if they have the same date, timeslot, and patient.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Appointment other = (Appointment) obj;
        return this.date.equals(other.date) &&
                this.timeslot.equals(other.timeslot) &&
                this.patient.equals(other.patient);
    }
    /**
     * Override method for toString(), used to return a string representation of the appointment.
     * It returns the date, timeslot, patient, and provider details.
     */
    @Override
    public String toString() {
        return String.format("%s %s %s [%s",
                this.date,
                this.timeslot,
                this.patient,
                this.provider);
    }
}