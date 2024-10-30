/**
 * @author Noor Mashal
 */
package model.util;

import model.clinic.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Scanner;

import static model.util.Sort.*;

public class List<E> implements Iterable<E> {
    private static final int INITIAL_CAPACITY = 4;
    private static final int NOT_FOUND = -1;

    private static final List<Technician> technicianList = new List<>();
    private static final List<Provider> providerList = new List<>();

    private static void initializeAppointmentList() {
        if (appointmentList == null) {
            appointmentList = new List<>();
        }
    }
    private static List<Appointment> appointmentList = null;

    private E[] objects;
    private int size; /* number of objects in the array */
    private static int currentTechnicianIndex = 0;

    /* Constructor */
    public List() {
        this.objects = (E[]) new Object[INITIAL_CAPACITY]; /* Create a generic array */
        this.size = 0;
    }

    /* Method to find an object in the list */
    private int find(E e) {
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i; // found
            }
        }
        return NOT_FOUND; /* not found */
    }

    /* Method to check if the list is empty */
    private boolean isEmpty() {
        return size == 0;
    }

    /* Method to return the number of objects in the list */
    public int size() {
        return size;
    }

    /* Method to get an object at a specific index */
    public E get(int index) {
        if (index >= 0 && index < size) {
            return objects[index];
        } else {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

    /* Method to set an object at a specific index */
    public void set(int index, E e) {
        if (index >= 0 && index < size) {
            objects[index] = e;
        } else {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
    }

    /* Method to grow the capacity of the array */
    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length + INITIAL_CAPACITY];
        for (int i = 0; i < objects.length; i++) {
            newObjects[i] = objects[i];
        }
        objects = newObjects;
    }

    /* Method to check if an object already exists */
    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }

    /* Method to add a new object to the end of the array */
    public void add(E e) {
        if (contains(e)) {
            return; /* Prevent adding duplicates */
        }
        if (size == objects.length) {
            grow(); /* Grow the array if it's full */
        }
        objects[size++] = e;
    }

    /* Method to remove an object from the list */
    public void remove(E e) {
        int index = find(e);
        if (index != NOT_FOUND) {
            for (int i = index; i < size - 1; i++) {
                objects[i] = objects[i + 1];
            }
            objects[--size] = null; /* Clear the last object */
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /* Method to print imaging appointments */
    public static String printImagingAppointments() {
        /* Check if the appointment list is empty */
        if (appointmentList == null || appointmentList.size() == 0) {
            return "Schedule calendar is empty.\n";
        }

        /* Sort the appointments first */
        sortImagingAppointments(appointmentList, appointmentList.size);

        /* Build the result to return */
        StringBuilder result = new StringBuilder();
        result.append("\n** List of radiology appointments ordered by county/date/time.\n");
        for (int i = 0; i < appointmentList.size; i++) {
            Appointment appointment = appointmentList.get(i);
            if (appointment instanceof Imaging) {
                Imaging imagingAppointment = (Imaging) appointment;
                result.append(imagingAppointment).append('\n');
            }
        }
        result.append("** end of list **\n");
        return result.toString();
    }
    public static String printOfficeAppointments() {
        /* Check if the appointment list is empty */
        if (appointmentList == null || appointmentList.size() == 0) {
            return "Schedule calendar is empty.\n";
        }

        sortOfficeAppointments(appointmentList, appointmentList.size);

        /* Build the result to return */
        StringBuilder result = new StringBuilder();
        result.append("\n** List of office appointments ordered by county/date/time.\n");
        for (int i = 0; i < appointmentList.size; i++) {
            Appointment appointment = appointmentList.get(i);
            if (appointment.getProvider() instanceof Doctor) {
                result.append(appointment).append('\n');
            }
        }
        result.append("** end of list **\n");
        return result.toString();
    }


    /* Inner class for the iterator */
        private class ListIterator implements Iterator<E> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            /* In a circular list, there is always a next element */
            return size > 0;
        }

        @Override
        public E next() {
            if (size == 0) {
                throw new IndexOutOfBoundsException("No elements to iterate.");
            }

            E element = objects[currentIndex]; /* Get the current element */

            /* Move to the next index, and if it reaches the end, wrap around */
            currentIndex = (currentIndex + 1) % size;

            return element;
        }
    }
    public static String printByAppointment() {
        /* Check if the appointment list is empty */
        if (appointmentList == null || appointmentList.size() == 0) {
            return "Schedule calendar is empty.\n";
        }

        /* Sort appointments by date/time/provider */
        sortAppointments(appointmentList, appointmentList.size);

        /* Build the result to return */
        StringBuilder result = new StringBuilder();
        result.append("\n** List of appointments, ordered by date/time/provider.\n");

        /* Append sorted appointments to the result */
        for (int i = 0; i < appointmentList.size(); i++) {
            result.append(appointmentList.get(i).toString()).append("\n");
        }

        result.append("** end of list **\n");
        return result.toString();
    }
    // In List.java
    public void loadProviders(String filePath) {
        loadFromFile(filePath);
    }


    /* Method to load data from providers.txt */
    private String loadFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split("\\s+");
                /* Assuming the line is formatted for either Doctor or Technician */
                if (tokens[0].equals("D")) {
                    /* Create a Doctor object */
                    Doctor doctor = new Doctor(tokens[1], tokens[2], parseDate(tokens[3]),
                            Location.valueOf(tokens[4].toUpperCase()), /* Use valueOf instead of new */
                            Specialty.valueOf(tokens[5].toUpperCase()), /* Use valueOf for Specialty */
                            tokens[6]);
                    add((E) doctor); /* Add to the list */
                } else if (tokens[0].equals("T")) {
                    /* Create a Technician object */
                    Technician technician = new Technician(tokens[1], tokens[2], parseDate(tokens[3]), Location.valueOf(tokens[4].toUpperCase()), Integer.parseInt(tokens[5]));
                    add((E) technician);
                    technicianList.add((technician)); /* Add to the list */
                }
            }
            scanner.close(); /* Close the scanner */
            return null;
        } catch (FileNotFoundException e) {
            return "Error: File not found - " + e.getMessage() + '\n';
        }
    }
    /**
     * Method to create Appointment
     * @param appointmentDate
     * @param timeslot
     * @param firstName
     * @param lastName
     * @param dob
     * @param provider
     * @return Appointment type
     */
    private static Appointment createAppointment(Date appointmentDate,
                                                 Timeslot timeslot,
                                                 String firstName, String lastName,
                                                 Date dob,
                                                 Provider provider) {

        /* Create the profile */
        Profile patient = Profile.createProfile(firstName, lastName, dob);

        /* Create and return the appointment */
        return new Appointment(appointmentDate, timeslot, patient, provider);
    }
    private static Doctor getDoctorByNPI(String npi) {
        for (int i = 0; i < providerList.size(); i++) {
            Provider provider = providerList.get(i); /* Iterate through provider list to look for Doctor matching NPI */
            if (provider instanceof Doctor) {
                Doctor doctor = (Doctor) provider;
                if (doctor.getNpi().equals(npi)) {
                    return doctor; /* Return the doctor if NPI matches */
                }
            }
        }
        return null; /* Return null if no doctor with that NPI is found */
    }
    /**
     * Checks if the given date at specific
     * timeslot and provider is available and valid
     * @param appointmentDate
     * @param timeslot
     * @param provider
     * @param profile
     * @return
     */
    private String isTimeslotBooked(Date appointmentDate, Timeslot timeslot, Provider provider, Profile profile) {
        for (int i = 0; i < size; i++) {
            Appointment existingAppointment = (Appointment) objects[i];

            /* Check if the date matches */
            if (existingAppointment.getDate().equals(appointmentDate) && existingAppointment.getTimeslot().equals(timeslot)) {
                /* Check if the timeslot matches */
                if (existingAppointment.getPatient().getFullName().equals(profile.getFullName()) && existingAppointment.getPatient().getDob().equals(profile.getDob())) {
                    return profile + " has an existing appointment at the same time slot.\n"; /** @return Indicates Timeslot is not Available */
                }
                else if (existingAppointment.getProvider().getProfile().getFullName().equals(provider.getProfile().getFullName())) {
                    return String.format("[%s is not available at %s\n",provider, timeslot); /** @return Indicates Timeslot is not Available */
                }
            }
        }
        return null; /** @return Indicates Timeslot is Available */
    }
    private String isRTimeslotBooked(Date appointmentDate, Timeslot timeslot, Provider provider, Profile profile) {
        for (int i = 0; i < size; i++) {
            Appointment existingAppointment = (Appointment) objects[i];

            /* Check if the date matches */
            if (existingAppointment.getDate().equals(appointmentDate) && existingAppointment.getTimeslot().equals(timeslot)) {
                /* Check if the timeslot matches */
                if (existingAppointment.getPatient().getFullName().equals(profile.getFullName()) && existingAppointment.getPatient().getDob().equals(profile.getDob())) {
                    return String.format("%s has an existing appointment at %s %s\n", profile, appointmentDate, timeslot); /** @return Indicates Timeslot is not Available */
                }
                else if (existingAppointment.getProvider().getProfile().getFullName().equals(provider.getProfile().getFullName())) {
                    return String.format("[%s is not available at %s\n",provider, timeslot); /** @return Indicates Timeslot is not Available */
                }
            }
        }
        return null; /** @return Indicates Timeslot is Available */
    }

    private String isPatientBooked(Date appointmentDate, Timeslot timeslot, Profile profile) {
        for (int i = 0; i < size; i++) {
            Appointment existingAppointment = (Appointment) objects[i];

            /* Check if the date matches */
            if (existingAppointment.getDate().equals(appointmentDate) && existingAppointment.getTimeslot().equals(timeslot)) {
                /* Check if the timeslot matches */
                if (existingAppointment.getPatient().equals(profile)) {
                    return profile + " has an existing appointment at the same time slot.\n"; /** @return Indicates Timeslot is not Available */
                }
            }
        }
        return null; /** @return Indicates Timeslot is Available */
    }


    /* Method to parse the date string */
    private Date parseDate(String dateStr) {
        String[] parts = dateStr.split("/");
        return new Date(Integer.parseInt(parts[2]), Integer.parseInt(parts[0]), Integer.parseInt(parts[1])); // Assuming Date class has this constructor
    }

    public static String printRotationList() {
        /* Build the result to return */
        StringBuilder result = new StringBuilder();
        result.append("\nRotation list for the technicians.\n");
//        System.out.print(technicianList.get(technicianList.size()-1).getProfile().getFullName() + " (" + technicianList.get(technicianList.size()-1).getLocation().name() + ")");
        result.append(technicianList.get(technicianList.size() - 1).getProfile().getFullName()).append(" (").append(technicianList.get(technicianList.size() - 1).getLocation().name()).append(")");
        for (int i = technicianList.size()-2; i >= 0; i--) {
            result.append(" --> ").append(technicianList.get(i).getProfile().getFullName()).append(" (").append(technicianList.get(i).getLocation().name()).append(")");
        }
        currentTechnicianIndex = technicianList.size()-1;
        result.append('\n');
        return result.toString();
    }

    private static String isOfficeInputValid (String[] parts) {
        if (parts.length != 7) {
            return "Missing data tokens.\n"; }
        String isDateValid = Date.isValid(parts[1], false);
        if (isDateValid != null) { return isDateValid; }
        if (Timeslot.getTimeslotFromInput(parts[2]) == null) { return parts[2] + " is not a valid time slot.\n"; }
        isDateValid = Date.isValid(parts[5], true);
        if (Date.isValid(parts[5], true) != null) { return isDateValid; }
        if (getDoctorByNPI(parts[6]) == null) { return parts[6] + " - provider doesn't exist.\n"; }
        return null;
    }
    private static String isImagingInputValid (String[] parts) {
        if (parts.length != 7) { return "Missing data tokens.\n"; }
        String isValid = Date.isValid(parts[1], false);
        if (isValid != null) { return isValid; }
        if (Timeslot.getTimeslotFromInput(parts[2]) == null) { return parts[2] + " is not a valid time slot.\n"; }
        isValid = Date.isValid(parts[5], true);
        if (isValid != null) { return isValid; }
        if (Radiology.fromString(parts[6]) == null) { return parts[6] + " - imaging service not provided.\n"; }
        return null;
    }
    private boolean isDoctorAvailable(Provider provider, Date appointmentDate, Timeslot timeslot) {
        /* Iterate through the appointment list to see if the doctor is already booked at the given timeslot and date */
        for (int i = 0; i < size; i++) {
            Appointment appointment = (Appointment) objects[i];
            if (appointment.getProvider().equals(provider)) {
                /* Check if the appointment date and timeslot match */
                if (appointment.getDate().equals(appointmentDate) && appointment.getTimeslot().equals(timeslot)) {
                    return false; /* Doctor is not available */
                }
            }
        }
        return true; /* Doctor is available */
    }


    public static String start(String filepath) {

        /* Load data from the file */
        String result1 = providerList.loadFromFile(filepath);
        if (result1 != null) {
            return result1;
        }
        provider(providerList);
        StringBuilder result = new StringBuilder();
        // Print the loaded providers to verify
        result.append("Providers loaded to the list.\n");
        for (int i = 0; i < providerList.size(); i++) {
            Provider provider = providerList.get(i);
            result.append("[").append(provider).append('\n');
        }
        result.append(printRotationList());
        return result.toString() + '\n';
    }
    public static String providerDrop() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < providerList.size(); i++) {
            Provider provider = providerList.get(i);
            if (provider instanceof Doctor) {
                result.append(provider.getProfile().getFullName()).append(" (").append(((Doctor) provider).getNpi()).append(')').append('\n');
            }
        }
        return result.toString();
    }

    public static String createAndAddOfficeAppointment(String[] input) {
        initializeAppointmentList();
        String isValid = isOfficeInputValid(input);
        if (isValid != null) { return isValid; } /* Check if valid input */
        Date appointmentDate = Date.stringToDate(input[1]);
        Timeslot timeslot = Timeslot.getTimeslotFromInput(input[2]);
        Doctor provider = getDoctorByNPI(input[6]);
        /** Check if the timeslot for the given date is already booked */
        isValid = appointmentList.isTimeslotBooked(appointmentDate, timeslot, provider, Profile.createProfile(input[3], input[4], Date.stringToDate(input[5])));
        if (isValid != null) {
            return isValid; /** @return Don't add the appointment */
        }
        if (!appointmentList.isDoctorAvailable(provider, appointmentDate, timeslot)) {
            return provider + " is not available at " + timeslot;
        }
        Appointment appointment = createAppointment(appointmentDate, timeslot, input[3], input[4], Date.stringToDate(input[5]), provider);
        appointmentList.add(appointment);
        return appointment + " booked.\n";
    }
    /**
     * Method to schedule the next available technician from the rotating list.
     * It returns the current technician and moves to the next one in a circular manner.
     */
    private static Technician scheduleNextTechnician(Date appointmentDate, Timeslot timeslot, Radiology room) {
        if (technicianList.isEmpty()) {
            return null;
        }
        // Get the current technician based on the currentTechnicianIndex
        if (currentTechnicianIndex >= technicianList.size()) {
            currentTechnicianIndex = 0;
        }
        Technician technician = technicianList.get(currentTechnicianIndex);
        if (appointmentList.isTechnicianAvailable(technician, appointmentDate, timeslot, room)) {
            currentTechnicianIndex = currentTechnicianIndex - 1;
            if (currentTechnicianIndex < 0) {
                currentTechnicianIndex = technicianList.size() - 1;
            }
            return technician;
        }
        currentTechnicianIndex = currentTechnicianIndex - 1;
        if (currentTechnicianIndex < 0) {
            currentTechnicianIndex = technicianList.size() - 1;
        }
        Technician currTech = technicianList.get(currentTechnicianIndex);
        while (!currTech.equals(technician)) {
            if (appointmentList.isTechnicianAvailable(currTech, appointmentDate, timeslot, room)) {
                currentTechnicianIndex = currentTechnicianIndex - 1;
                if (currentTechnicianIndex < 0) {
                    currentTechnicianIndex = technicianList.size() - 1;
                }
                return currTech;
            }
            currTech = technicianList.get(currentTechnicianIndex);
            currentTechnicianIndex = currentTechnicianIndex - 1;
            if (currentTechnicianIndex < 0) {
                currentTechnicianIndex = technicianList.size() - 1;
            }
        }
        return null;
    }
    private boolean isTechnicianAvailable(Technician currTech, Date appointmentDate, Timeslot timeslot, Radiology room) {
        // Iterate through the appointment list to see if the technician is booked at the given timeslot
        for (int i = 0; i < size; i++) {
            Appointment appointment = (Appointment) objects[i];
            if (appointment instanceof Imaging imagingAppointment) {

                // Check if the appointment date and timeslot match the desired date and timeslot
                if (imagingAppointment.getDate().equals(appointmentDate)
                        && imagingAppointment.getTimeslot().equals(timeslot)
                        && imagingAppointment.getProvider().getLocation().equals(currTech.getLocation())
                        && imagingAppointment.getRoom().equals(room)) {
                    // System.out.println(currTech.getProfile().getFullName() + " is not available at this timeslot.");
                    return false; // Technician is not available
                }
            }
        }
        return true; // Technician is available
    }

    private boolean isTechnicianFree(Technician currTech, Date appointmentDate, Timeslot timeslot) {
        for (int i = 0; i < size; i++) {
            Appointment appointment = (Appointment) objects[i];
            if (appointment instanceof Imaging) {
                Imaging existingAppointment = (Imaging) appointment;

                /* Check if the date matches */
                if (existingAppointment.getDate().equals(appointmentDate) && existingAppointment.getTimeslot().equals(timeslot)) {
                    /* Check if the timeslot matches */
                    if (existingAppointment.getProvider().equals(currTech)) {
                        return false; /** @return Indicates Timeslot is not Available */
                    }
                }
            }
        }
            return true; /** @return Indicates Timeslot is Available */
    }

    /**
     * Method to schedule an imaging appointment with the next available technician
     * for a specified radiology service.
     *
     * @return true if the appointment was successfully scheduled, false otherwise.
     */
    public static String scheduleTechnicianImagingAppointment(String[] input) {
        initializeAppointmentList();
        String isValid = isImagingInputValid(input);
        if (isValid != null) { return isValid; } /* Check if valid input */

        Date appointmentDate = Date.stringToDate(input[1]);
        Timeslot timeslot = Timeslot.getTimeslotFromInput(input[2]);
        String patientFirstName = input[3];
        String patientLastName = input[4];
        Radiology room = Radiology.fromString(input[6]);
        int temp = currentTechnicianIndex;
        Technician technician = scheduleNextTechnician(appointmentDate, timeslot, room);
        while (!appointmentList.isTechnicianFree(technician, appointmentDate, timeslot)) {
            if (currentTechnicianIndex == temp) {
                break;
            }
            technician = scheduleNextTechnician(appointmentDate, timeslot, room);
        }
        if (!appointmentList.isTechnicianFree(technician, appointmentDate, timeslot)) {
            technician = scheduleNextTechnician(appointmentDate, timeslot, room);
        }
        if (technician == null) {
            currentTechnicianIndex = temp;
            return String.format("Cannot find an available technician at all locations for %s at %s.\n", input[6].toUpperCase(), input[2]);
        }
        // Check if the timeslot is already booked
        isValid = appointmentList.isPatientBooked(appointmentDate, timeslot, Profile.createProfile(input[3], input[4], Date.stringToDate(input[5])));
        if (isValid != null) {
            currentTechnicianIndex += 2;
            return isValid;
        }
        // Create a new Imaging appointment using the provided details
        Imaging imagingAppointment = new Imaging(appointmentDate, timeslot, patientFirstName, patientLastName, input[5], technician, room);

        // Add the imaging appointment to the appointment list
        appointmentList.add(imagingAppointment);


        // Print confirmation message
        return imagingAppointment + " booked.\n";
    }
    private static String isCInputValid (String[] parts) {
        if (parts.length != 6) { return "Missing data tokens.\n"; }
        String isValid = Date.isValid(parts[1], false);
        if (isValid != null) { return isValid; }
        if (Timeslot.getTimeslotFromInput(parts[2]) == null) { return parts[2] + " is not a valid time slot.\n"; }
        isValid = Date.isValid(parts[5], true);
        if (isValid != null) { return isValid; }
        return null;
    }
    public static String removeAppointment(String[] input) {
        /* Ensure the appointmentList exists before trying to remove */
        initializeAppointmentList();
        String isValid = isCInputValid(input);
        if (isValid != null) { return isValid; }
        Date appointmentDate = Date.stringToDate(input[1]);
        Timeslot timeslot = Timeslot.getTimeslotFromInput(input[2]);
        /* Find and remove the appointment from the list */
        for (int i = 0; i < appointmentList.size; i++) {
            Appointment currAppointment = appointmentList.get(i);
            if (currAppointment.getDate().equals(appointmentDate) && currAppointment.getTimeslot().equals(timeslot)) {
                if (currAppointment.getPatient().getDob().equals(Date.stringToDate(input[5])) && currAppointment.getPatient().getFullName().equalsIgnoreCase(input[3] + " " + input[4])) {
                    appointmentList.remove(currAppointment);
                    return String.format("%s %s %s %s %s - appointment has been canceled.\n",appointmentDate, timeslot, input[3], input[4], input[5]);
                }
            }
        }
        return String.format("%s %s %s %s %s - appointment does not exist.\n",appointmentDate, timeslot, input[3], input[4], input[5]);
    }
    public static String rescheduleAppointment(String[] input) {
        /* Ensure the appointmentList exists before trying to remove */
        initializeAppointmentList();
        String isValid = isRInputValid(input);
        if (isValid != null) { return isValid; }
        Date appointmentDate = Date.stringToDate(input[1]);
        Timeslot timeslot = Timeslot.getTimeslotFromInput(input[2]);
        String firstName = input[3]; /* Patient First Name */
        String lastName = input[4]; /* Patient Last Name */
        Date dob = Date.stringToDate(input[5]); /* Patient Date of Birth */
        Timeslot newTimeSlot = Timeslot.getTimeslotFromInput(input[6]);
        Profile patientProfile = Profile.createProfile(firstName, lastName, dob);
        Appointment currentAppointment = appointmentList.getAppointment(appointmentDate, timeslot, patientProfile);
        if (currentAppointment == null) {
            return String.format("%s %s %s does not exist.\n", appointmentDate, timeslot, patientProfile); /** Exit: @return Appointment does not exist */
        }
        Provider provider = currentAppointment.getProvider();
        /* Check if the new timeslot is valid and available for the same provider */
        isValid = appointmentList.isRTimeslotBooked(appointmentDate, newTimeSlot, provider, Profile.createProfile(input[3], input[4], Date.stringToDate(input[5])));
        if (isValid != null) {
            return isValid; /* Exit: Don't add the appointment */
        }
        /* Update the appointment with the new timeslot */
        currentAppointment.setTimeslot(newTimeSlot);
        return "Rescheduled to " + currentAppointment + '\n';
    }
    public static String printByPatient() {
        /* Check if the appointment list is empty */
        if (appointmentList == null || appointmentList.size() == 0) {
            return "Schedule calendar is empty.\n";
        }
        sortPatients(appointmentList, appointmentList.size);
        /* Build the result to return */
        StringBuilder result = new StringBuilder();
        result.append("\n** List of ordered by patient/date/time\n");
        /* Print sorted appointments */
        for (int i = 0; i < appointmentList.size(); i++) {
            result.append(appointmentList.get(i)).append('\n');
        }
        result.append("** end of list **\n");
        return result.toString();
    }
    private Appointment getAppointment(Date date, Timeslot timeslot, Profile patientProfile) {
        for (int i = 0; i < appointmentList.size(); i++) {
            if (appointmentList.get(i).getDate().equals(date) &&
                    appointmentList.get(i).getTimeslot().equals(timeslot) &&
                    appointmentList.get(i).getPatient().equals(patientProfile)) {
                return appointmentList.get(i); /* Appointment found */
            }
        }
        return null; /* Appointment not found */
    }
    public static String printByLocation() {
        /* Check if the appointment list is empty */
        if (appointmentList == null || appointmentList.size() == 0) {
            return "Schedule calendar is empty.\n";
        }

        /* Sort appointments by location */
        sortLocations(appointmentList, appointmentList.size);

        /* Build the result to return */
        StringBuilder result = new StringBuilder();
        result.append("\n** List of appointments, ordered by county/date/time.\n");

        /* Append sorted appointments to the result */
        for (int i = 0; i < appointmentList.size(); i++) {
            result.append(appointmentList.get(i).toString()).append("\n");
        }

        result.append("** end of list **\n");
        return result.toString();
    }

    private static String isRInputValid(String[] parts) {
        if (parts.length != 7) {
            return "Missing data tokens.\n"; }
        String isValid = Date.isValid(parts[1], false);
        if (isValid != null) { return isValid; }
        if (Timeslot.getTimeslotFromInput(parts[2]) == null) { return parts[2] + " is not a valid time slot."; }
        isValid = Date.isValid(parts[5], true);
        if (Date.isValid(parts[5], true) != null) { return isValid; }
        if (Timeslot.getTimeslotFromInput(parts[6]) == null) { return parts[6] + " is not a valid time slot."; }
        return null;
    }
    public static String displayBillingStatements() {
        /* Check if the appointment list is empty */
        if (appointmentList == null || appointmentList.size() == 0) {
            return "Schedule calendar is empty.\n";
        }

        /* Temporary arrays to hold patient names, their dates of birth, and total charges */
        String[] patientNames = new String[appointmentList.size()];
        Date[] patientDobs = new Date[appointmentList.size()]; // Store DOBs
        float[] totalCharges = new float[appointmentList.size()];
        int count = 0;

        /* Iterate through appointments to collect unique patients and their total charges */
        for (int i = 0; i < appointmentList.size(); i++) {
            Profile currentPatient = appointmentList.get(i).getPatient();
            boolean alreadyBilled = false;

            /* Check if this patient has already been billed */
            for (int j = 0; j < count; j++) {
                /* Compare by full name and DOB */
                if (patientNames[j].equals(currentPatient.getFirstName() + " " + currentPatient.getLastName())
                        && patientDobs[j].equals(currentPatient.getDob())) { // Ensure this method exists in Profile
                    alreadyBilled = true;
                    break;
                }
            }

            /* If patient has already been billed, skip to the next appointment */
            if (alreadyBilled) {
                continue;
            }

            /* Sum up all the charges for this patient */
            float totalCharge = 0;
            for (int k = i; k < appointmentList.size(); k++) {
                if (appointmentList.get(k).getPatient().equals(currentPatient)) {
                    Provider provider = appointmentList.get(k).getProvider();
                    totalCharge += provider.rate();
                }
            }

            /* Store the patient name, DOB, and total charge */
            patientNames[count] = currentPatient.getFirstName() + " " + currentPatient.getLastName();
            patientDobs[count] = currentPatient.getDob();
            totalCharges[count] = totalCharge;
            count++;
        }

        /* Sort the patient names, DOBs, and Corresponding Charges */
        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                /* Compare by last name, then first name, then DOB */
                String[] name1 = patientNames[i].split(" ");
                String[] name2 = patientNames[j].split(" ");
                int comparison = name1[1].compareTo(name2[1]); // Compare last names

                if (comparison == 0) { /* If last names are the same, compare first names */
                    comparison = name1[0].compareTo(name2[0]);
                }

                if (comparison == 0) { /* If names are the same, compare DOB */
                    comparison = patientDobs[i].compareTo(patientDobs[j]);
                }

                if (comparison > 0) { /* If name1 comes after name2, swap */
                    /* Swap names */
                    String tempName = patientNames[i];
                    patientNames[i] = patientNames[j];
                    patientNames[j] = tempName;

                    /* Swap DOBs */
                    Date tempDob = patientDobs[i];
                    patientDobs[i] = patientDobs[j];
                    patientDobs[j] = tempDob;

                    /* Swap corresponding charges */
                    float tempCharge = totalCharges[i];
                    totalCharges[i] = totalCharges[j];
                    totalCharges[j] = tempCharge;
                }
            }
        }

        /* Create a DecimalFormat instance for formatting currency */
        DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00");

        /* Build the result to return */
        StringBuilder result = new StringBuilder();
        result.append("\n** Billing statement ordered by patient. **\n");
        for (int i = 0; i < count; i++) {
            result.append(String.format("(%d) %s %s [due: %s]",
                    i + 1,
                    patientNames[i],
                    patientDobs[i],
                    decimalFormat.format(totalCharges[i]))).append('\n');
        }
        result.append("** end of list **\n");
        appointmentList = null;
        return result.toString();
    }
    public static String displayCreditAmounts() {
        /* Check if the appointment list is empty */
        if (appointmentList == null || appointmentList.size() == 0) {
            return "Schedule calendar is empty.\n";
        }

        /* Temporary arrays to hold provider names, their dates of birth, and total credits */
        String[] providerNames = new String[appointmentList.size()];
        Date[] providerDobs = new Date[appointmentList.size()]; // Store DOBs
        float[] totalCredits = new float[appointmentList.size()];
        int count = 0;

        /* Iterate through appointments to collect unique providers and their total credits */
        for (int i = 0; i < appointmentList.size(); i++) {
            Provider currentProvider = appointmentList.get(i).getProvider();
            boolean alreadyBilled = false;

            /* Check if this provider has already been billed */
            for (int j = 0; j < count; j++) {
                /* Compare by full name and DOB */
                if (providerNames[j].equals(currentProvider.getProfile().getFirstName() + " " + currentProvider.getProfile().getLastName())
                        && providerDobs[j].equals(currentProvider.getProfile().getDob())) { // Ensure this method exists in Profile
                    alreadyBilled = true;
                    break;
                }
            }

            /* If provider has already been billed, skip to the next appointment */
            if (alreadyBilled) {
                continue;
            }

            /* Sum up all the credits for this provider */
            float totalCredit = 0;
            for (int k = i; k < appointmentList.size(); k++) {
                if (appointmentList.get(k).getProvider().equals(currentProvider)) {
                    totalCredit += currentProvider.rate(); // Assuming the rate() method returns the credit amount
                }
            }

            /* Store the provider name, DOB, and total credit */
            providerNames[count] = currentProvider.getProfile().getFirstName() + " " + currentProvider.getProfile().getLastName();
            providerDobs[count] = currentProvider.getProfile().getDob();
            totalCredits[count] = totalCredit;
            count++;
        }

        /* Sort the provider names, DOBs, and Corresponding Credits */
        for (int i = 0; i < count - 1; i++) {
            for (int j = i + 1; j < count; j++) {
                /* Compare by last name, then first name, then DOB */
                String[] name1 = providerNames[i].split(" ");
                String[] name2 = providerNames[j].split(" ");
                int comparison = name1[1].compareTo(name2[1]); // Compare last names

                if (comparison == 0) { /* If last names are the same, compare first names */
                    comparison = name1[0].compareTo(name2[0]);
                }

                if (comparison == 0) { /* If names are the same, compare DOB */
                    comparison = providerDobs[i].compareTo(providerDobs[j]);
                }

                if (comparison > 0) { /* If name1 comes after name2, swap */
                    /* Swap names */
                    String tempName = providerNames[i];
                    providerNames[i] = providerNames[j];
                    providerNames[j] = tempName;

                    /* Swap DOBs */
                    Date tempDob = providerDobs[i];
                    providerDobs[i] = providerDobs[j];
                    providerDobs[j] = tempDob;

                    /* Swap corresponding credits */
                    float tempCredit = totalCredits[i];
                    totalCredits[i] = totalCredits[j];
                    totalCredits[j] = tempCredit;
                }
            }
        }

        /* Create a DecimalFormat instance for formatting currency */
        DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00");

        /* Build the result to return */
        StringBuilder result = new StringBuilder();
        result.append("\n** Credit amount ordered by provider. **\n");
        for (int i = 0; i < count; i++) {
            result.append(String.format("(%d) %s %s [credit amount: %s]",
                    i + 1,
                    providerNames[i],
                    providerDobs[i],
                    decimalFormat.format(totalCredits[i]))).append('\n');
        }
        result.append("** end of list **\n");
        return result.toString();
    }
}