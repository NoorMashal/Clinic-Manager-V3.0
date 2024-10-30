/**
 * @author Noor Mashal
 */
package model.clinic;

public enum Radiology {
    CATSCAN,      /* CAT scan service. */
    ULTRASOUND,   /* Ultrasound service. */
    XRAY;         /* X-ray service. */

    /**
     * Convert a string to the corresponding Radiology enum constant.
     *
     * @param service the string representing the radiology service (case-insensitive)
     * @return the matching Radiology enum constant, or null if no match is found
     */
    public static Radiology fromString(String service) {
        if (service == null) {
            return null;
        }
        return switch (service.toUpperCase()) {
            case "CATSCAN" -> CATSCAN;
            case "ULTRASOUND" -> ULTRASOUND;
            case "XRAY" -> XRAY;
            default -> {
                System.out.println(service + " - imaging service not provided.");
                yield null;
            }
        };
    }
}