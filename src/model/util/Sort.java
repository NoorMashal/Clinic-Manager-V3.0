/**
 * @author Noor Mashal
 */
package model.util;

import model.clinic.Appointment;
import model.clinic.Imaging;
import model.clinic.Provider;

public class Sort {

    private static List<Appointment> appointmentList = null;
    private static int size;
    public static void sortImagingAppointments(List<Appointment> appointmentList2, int size1) {
        appointmentList = appointmentList2;
        size = size1;
        if (appointmentList == null || size <= 1) {
            return; // No need to sort if the list is empty or has only one element
        }

        // Perform a bubble sort to sort appointments
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                Appointment a1 = appointmentList.get(j);
                Appointment a2 = appointmentList.get(j + 1);

                // Only sort imaging appointments
                if (a1 instanceof Imaging && a2 instanceof Imaging) {
                    Imaging imaging1 = (Imaging) a1;
                    Imaging imaging2 = (Imaging) a2;

                    // Compare by county
                    int countyCompare = imaging1.getProvider().getLocation().getCounty()
                            .compareTo(imaging2.getProvider().getLocation().getCounty());

                    if (countyCompare > 0) {
                        // Swap if out of order
                        swap(j, j + 1);
                    } else if (countyCompare == 0) {
                        // If counties are the same, compare by date
                        int dateCompare = imaging1.getDate().compareTo(imaging2.getDate());

                        if (dateCompare > 0) {
                            // Swap if out of order
                            swap(j, j + 1);
                        } else if (dateCompare == 0) {
                            // If dates are the same, compare by timeslot
                            int timeslotCompare = imaging1.getTimeslot().compareTo(imaging2.getTimeslot());
                            if (timeslotCompare > 0) {
                                // Swap if out of order
                                swap(j, j + 1);
                            }
                        }
                    }
                }
            }
        }
    }
    public static void sortOfficeAppointments(List<Appointment> appointmentList2, int size1) {
        appointmentList = appointmentList2;
        size = size1;
        if (appointmentList == null || size <= 1) {
            return; // No need to sort if the list is empty or has only one element
        }

        // Perform a bubble sort to sort appointments
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                Appointment a1 = appointmentList.get(j);
                Appointment a2 = appointmentList.get(j + 1);

                // Compare by county
                int countyCompare = a1.getProvider().getLocation().getCounty()
                        .compareTo(a2.getProvider().getLocation().getCounty());

                if (countyCompare > 0) {
                    // Swap if out of order
                    swap(j, j + 1);
                } else if (countyCompare == 0) {
                    // If counties are the same, compare by date
                    int dateCompare = a1.getDate().compareTo(a2.getDate());

                    if (dateCompare > 0) {
                        // Swap if out of order
                        swap(j, j + 1);
                    } else if (dateCompare == 0) {
                        // If dates are the same, compare by timeslot
                        int timeslotCompare = a1.getTimeslot().compareTo(a2.getTimeslot());
                        if (timeslotCompare > 0) {
                            // Swap if out of order
                            swap(j, j + 1);
                        }
                    }
                }
            }
        }
    }
    public static void sortAppointments(List<Appointment> appointmentList2, int size1) {
        appointmentList = appointmentList2;
        size = size1;
        if (appointmentList == null || size <= 1) {
            return; // No need to sort if the list is empty or has only one element
        }

        /* Sort appointments using bubble sort */
        for (int i = 0; i < appointmentList.size() - 1; i++) {
            for (int j = 0; j < appointmentList.size() - 1 - i; j++) {
                Appointment current = appointmentList.get(j);
                Appointment next = appointmentList.get(j + 1);

                /* Compare by appointment date */
                if (current.getDate().compareTo(next.getDate()) > 0) {
                    swap(j, j + 1);

                } else if (current.getDate().equals(next.getDate())) {

                    /* If dates are the same, compare by timeslot */
                    if (current.getTimeslot().compareTo(next.getTimeslot()) > 0) {
                        swap(j, j + 1);
                    } else if (current.getTimeslot().equals(next.getTimeslot())) {
                        /* If timeslots are the same, compare by provider name */
                        if (current.getProvider().compareTo(next.getProvider()) > 0) {
                            swap(j, j + 1);
                        }
                    }
                }
            }
        }
    }
    public static void sortPatients(List<Appointment> appointmentList2, int size1) {
        appointmentList = appointmentList2;
        size = size1;
        for (int i = 0; i < appointmentList.size() - 1; i++) {
            for (int j = 0; j < appointmentList.size() - 1 - i; j++) {
                /* Compare based on the patient, date of birth, appointment date, and timeslot */
                if (compareByPatient(appointmentList.get(j), appointmentList.get(j + 1)) > 0) {
                    swap(j, j + 1); /* Swap the appointments if they are out of order */
                }
            }
        }
    }
    public static void sortLocations(List<Appointment> appointmentList2, int size1) {
        appointmentList = appointmentList2;
        size = size1;
        for (int i = 0; i < appointmentList.size() - 1; i++) {
            for (int j = 0; j < appointmentList.size() - 1 - i; j++) {
                /* Compare Based on the County Name, Appointment Date, and Timeslot */
                if (compareByCounty(appointmentList.get(j), appointmentList.get(j + 1)) > 0) {
                    swap(j, j + 1); /* Swap the appointments if they are out of order */
                }
            }
        }
    }

    /* Helper Method to Compare Two Appointments Based on Patient Profile, DOB, Appointment Date, and Timeslot */
    private static int compareByPatient(Appointment a1, Appointment a2) {
        /* Compare by last name */
        int lastNameComparison = a1.getPatient().getLastName().compareTo(a2.getPatient().getLastName());
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }

        /* If last names are equal, compare by first name */
        int firstNameComparison = a1.getPatient().getFirstName().compareTo(a2.getPatient().getFirstName());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }

        /* If first names are equal, compare by date of birth */
        int dobComparison = a1.getPatient().getDob().compareTo(a2.getPatient().getDob());
        if (dobComparison != 0) {
            return dobComparison;
        }

        /* If DOBs are equal, compare by appointment date */
        int appointmentDateComparison = a1.getDate().compareTo(a2.getDate());
        if (appointmentDateComparison != 0) {
            return appointmentDateComparison;
        }

        /* If appointment dates are equal, compare by timeslot */
        return a1.getTimeslot().compareTo(a2.getTimeslot());
    }

    /* Helper method to compare two appointments based on county, appointment date, and timeslot */
    private static int compareByCounty(Appointment a1, Appointment a2) {
        // Compare by provider's county (location)
        int countyComparison = a1.getProvider().getLocation().getCounty().compareTo(a2.getProvider().getLocation().getCounty());
        if (countyComparison != 0) {
            return countyComparison;
        }

        /* If county names are equal, compare by appointment date */
        int appointmentDateComparison = a1.getDate().compareTo(a2.getDate());
        if (appointmentDateComparison != 0) {
            return appointmentDateComparison;
        }

        /* If appointment dates are equal, compare by timeslot */
        return a1.getTimeslot().compareTo(a2.getTimeslot());
    }
    // Method to swap two elements in the appointment list
    private static void swap(int i, int j) {
        Appointment temp = appointmentList.get(i);
        appointmentList.set(i, appointmentList.get(j));
        appointmentList.set(j, temp);
    }
    // Sorts the providers by last name in reverse order (Z-A)
    public static void provider(List<Provider> list) {
        // Implementing a simple Bubble Sort for demonstration
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Provider p1 = list.get(j);
                Provider p2 = list.get(j + 1);

                String lastName1 = p1.getProfile().getLastName();
                String lastName2 = p2.getProfile().getLastName();

                // If lastName1 comes after lastName2 alphabetically (Z-A), swap
                if (lastName2.compareTo(lastName1) < 0) {
                    // Swap providers
                    Provider temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}