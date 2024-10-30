/**
 * @author Noor Mashal
 */

package model.clinic;

public class Visit {
    private Appointment appointment;  /* reference to appointment */
    private Visit next;  /* reference next appointment list */

    public Visit(Appointment appointment) {
        this.appointment = appointment;
        this.next = null;
    }

    /* Getter for Test.Appointment */
    public Appointment getAppointment() {
        return appointment;
    }

    /* Getter for Next Test.Visit */
    public Visit getNext() {
        return next;
    }

    /* Allows for Linking Visits */
    public void setNext(Visit next) {
        this.next = next;
    }
}