/**
 * @author Noor Mashal
 */

package model.clinic;

/* List of Specialties */
public enum Specialty {
    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    private final int charge;

    private Specialty(int charge) {
        this.charge = charge;
    }

    public int getCharge() {
        return charge;
    }

    @Override
    public String toString() {
        return String.format("%s", name());
    }
}
