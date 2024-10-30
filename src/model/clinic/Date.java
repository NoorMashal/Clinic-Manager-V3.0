/**
 * @author Noor Mashal
 */

package model.clinic;

import java.util.Calendar;

public class Date implements Comparable<Date> {
    /* Fields for year, month, and day to represent a date */
    private final int year;
    private final int month;
    private final int day;

    /* initialize the Date object */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /* method to check if a Calendar date is valid */
    public static String isValid(String dateFromInput, boolean isDob) {
        int month;
        int day;
        int year;
        try {
            String[] dateParts = dateFromInput.split("/");
            month = Integer.parseInt(dateParts[0]);
            day = Integer.parseInt(dateParts[1]);
            year = Integer.parseInt(dateParts[2]);
            month = month - 1; /* accounts for January being month 0 */
            Calendar inputDate = Calendar.getInstance();
            inputDate.setLenient(false);
            inputDate.set(year, month, day);
            inputDate.getTime(); /* Will throw an exception if the date is invalid */
            Calendar today = Calendar.getInstance();

            /* Checks if dob is today or after today. */
            if ((isDob) && (!inputDate.before(today))) {
                System.out.println("Patient dob: " + dateFromInput + " is today or a date after today.");
                return "Patient dob: " + dateFromInput + " is today or a date after today.\n"; /* Date is Invalid */
            }

            /* Checks if Appointment date is before today. */
            if ((!isDob) && (!inputDate.after(today))) {
                System.out.println("Appointment date: " + dateFromInput + " is today or a date before today.");
                return "Appointment date: " + dateFromInput + " is today or a date before today.\n"; /* Date is Invalid. */
            }

            /* Check if the appointment date is on a weekend. */
            int dayOfWeek = inputDate.get(Calendar.DAY_OF_WEEK);
            if (!isDob && (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)) {
                System.out.println("Appointment date: " + dateFromInput + " is Saturday or Sunday.");
                return "Appointment date: " + dateFromInput + " is Saturday or Sunday.\n"; /* @return Date is Invalid. */
            }

            Calendar sixMonthsFromToday = Calendar.getInstance();
            sixMonthsFromToday.add(Calendar.MONTH, 6);

            /* Checks if the appointment date is within 6 months. */
            if ((!isDob) && !(inputDate.before(sixMonthsFromToday))) {
                System.out.println("Appointment date: " + dateFromInput + " is not within six months.");
                return "Appointment date: " + dateFromInput + " is not within six months.\n"; /* @return Date is Invalid. */
            }
            return null; // Date is Valid
        } catch (Exception e) {
            if (isDob) {
                System.out.print("Patient dob: ");
                return "Patient dob: " + dateFromInput + " is not a valid calendar date \n";
            } /* Checks if the date is dob or Appointment Date */
            else {
                System.out.print("Appointment date: ");
                return "Appointment date: " + dateFromInput + " is not a valid calendar date \n";
            } /* Date has to be appointment date */
        }
    }

    public static Date stringToDate(String stringDate) {
        String[] dateParts = stringDate.split("/");
        return new Date(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]));
    }

    /**
     * Override method for compareTo() | equals() | toString()
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year; // Compare by year
        }
        if (this.month != other.month) {
            return this.month - other.month; /* Compare by month */
        }
        return this.day - other.day; /* Compare by day */
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Date date = (Date) obj;
        return year == date.year && month == date.month && day == date.day;
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d", month, day, year);
    }
}