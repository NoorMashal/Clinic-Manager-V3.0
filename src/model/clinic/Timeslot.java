/**
 * @author Noor Mashal
 */
package model.clinic;

public class Timeslot implements Comparable<Timeslot> {
    private int hour;
    private int minute;

    /* Constructor to initialize the hour and minute */
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /* Getter for the hour */
    public int getHour() {
        return hour;
    }

    /* Getter for the minute */
    public int getMinute() {
        return minute;
    }

    /* Equals method to compare two Timeslot objects */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Timeslot other = (Timeslot) obj;
        return this.hour == other.hour && this.minute == other.minute;
    }

    /* CompareTo method for comparing times */
    @Override
    public int compareTo(Timeslot other) {
        // Convert hours to 24-hour format before comparing
        int thisHour24 = convertTo24Hour(this.hour);
        int otherHour24 = convertTo24Hour(other.hour);

        if (thisHour24 != otherHour24) {
            return thisHour24 - otherHour24; // Compare the converted 24-hour format hours
        }
        return this.minute - other.minute; // If hours are the same, compare minutes
    }

    // Helper method to convert 12-hour format to 24-hour format
    private int convertTo24Hour(int hour) {
        if (hour == 9 || hour == 10 || hour == 11) {
            return hour; // Keep AM times as is
        }
        if (hour == 12) {
            return 12; // 12 PM stays as 12
        }
        return hour + 12; // Convert PM times (e.g., 2 PM -> 14, 3 PM -> 15, etc.)
    }


    // ToString method to represent the time to readable format */
    @Override
    public String toString() {
        int displayHour = hour % 12;
        if (displayHour == 0) displayHour = 12; // Handle 12-hour format
        String period = (hour < 12) ? "AM" : "PM"; // Fix to check for AM/PM correctly
        return String.format("%d:%02d %s", displayHour, minute, period);
    }

    /* Method to create a Timeslot from input */
    public static Timeslot getTimeslotFromInput(String slotNumber) {
        // Directly switch on slotNumber since it's already a string representing the timeslot
        return switch (slotNumber) {
            case "09:00 AM" -> new Timeslot(9, 0);
            case "09:30 AM" -> new Timeslot(9, 30);
            case "10:00 AM" -> new Timeslot(10, 0);
            case "10:30 AM" -> new Timeslot(10, 30);
            case "11:00 AM" -> new Timeslot(11, 0);
            case "11:30 AM" -> new Timeslot(11, 30);
            case "02:00 PM" -> new Timeslot(14, 0);  // Use 14 for 2 PM in 24-hour format
            case "02:30 PM" -> new Timeslot(14, 30);
            case "03:00 PM" -> new Timeslot(15, 0);
            case "03:30 PM" -> new Timeslot(15, 30);
            case "04:00 PM" -> new Timeslot(16, 0);
            case "04:30 PM" -> new Timeslot(16, 30);
            default -> {
//                System.out.println(slotNumber + " is not a valid time slot.");
                yield null;
            }
        };
    }
    public static int getSlotNumber(Timeslot timeslot) {
        if (timeslot.getHour() == 9 && timeslot.getMinute() == 0) return 1;
        if (timeslot.getHour() == 9 && timeslot.getMinute() == 30) return 2;
        if (timeslot.getHour() == 10 && timeslot.getMinute() == 0) return 3;
        if (timeslot.getHour() == 10 && timeslot.getMinute() == 30) return 4;
        if (timeslot.getHour() == 11 && timeslot.getMinute() == 0) return 5;
        if (timeslot.getHour() == 11 && timeslot.getMinute() == 30) return 6;
        if (timeslot.getHour() == 14 && timeslot.getMinute() == 0) return 7;  // 2 PM
        if (timeslot.getHour() == 14 && timeslot.getMinute() == 30) return 8;  // 2:30 PM
        if (timeslot.getHour() == 15 && timeslot.getMinute() == 0) return 9;  // 3 PM
        if (timeslot.getHour() == 15 && timeslot.getMinute() == 30) return 10; // 3:30 PM
        if (timeslot.getHour() == 16 && timeslot.getMinute() == 0) return 11; // 4 PM
        if (timeslot.getHour() == 16 && timeslot.getMinute() == 30) return 12; // 4:30 PM
//        System.out.println("This is the timeslot: " + timeslot.getHour());
        return -1; // If no valid slot matches
    }
}