/**
 * @author Noor Mashal
 */
package controller;

import model.util.List;
import model.clinic.Location;
import model.clinic.Radiology;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ClinicManagerController {

    @FXML
    private TextArea outputArea;

    @FXML
    private DatePicker aptDate;
    @FXML
    private DatePicker aptDateC;
    @FXML
    private DatePicker aptDateR;

    @FXML
    private ComboBox<String> timeslotDrop;
    @FXML
    private ComboBox<String> timeslotDropC;
    @FXML
    private ComboBox<String> timeslotDropR1;
    @FXML
    private ComboBox<String> timeslotDropR2;

    @FXML
    private ComboBox<String> providerImagingDrop;

    @FXML
    private TextField firstName;
    @FXML
    private TextField firstNameC;
    @FXML
    private TextField firstNameR;

    @FXML
    private TextField lastName;
    @FXML
    private TextField lastNameC;
    @FXML
    private TextField lastNameR;

    @FXML
    private DatePicker dob;
    @FXML
    private DatePicker dobC;
    @FXML
    private DatePicker dobR;

    @FXML
    private RadioButton office;

    @FXML
    private RadioButton imaging;

    @FXML
    private Button loadProviders;
    @FXML
    private Button loadProvidersC;
    @FXML
    private Button loadProvidersR;

    @FXML
    private TableView<Location> locationTableView;

    @FXML
    private TableColumn<Location, String> locationColumn;

    @FXML
    private TableColumn<Location, String> countyColumn;

    @FXML
    private TableColumn<Location, String> zipColumn;

    private ObservableList<Location> locationData = FXCollections.observableArrayList(Location.values());
    /**
     * Initializes the controller, loads providers from file, and sets up any necessary data.
     */
    @FXML
    public void initialize() {
        /* Populate the ComboBoxes for timeslot for each tab */
        timeslotDrop.getItems().addAll("09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM");
        timeslotDropC.getItems().addAll("09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM");
        timeslotDropR1.getItems().addAll("09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM");
        timeslotDropR2.getItems().addAll("09:00 AM", "09:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "02:00 PM", "02:30 PM", "03:00 PM", "03:30 PM", "04:00 PM", "04:30 PM");

        /* Group RadioButtons to ensure only one is selected at a time */
        ToggleGroup appointmentTypeGroup = new ToggleGroup();
        office.setToggleGroup(appointmentTypeGroup);
        imaging.setToggleGroup(appointmentTypeGroup);

        /* Add listeners to the RadioButtons to handle provider list updates */
        office.setOnAction(event -> loadOfficeProviders());
        imaging.setOnAction(event -> loadImagingProviders());

        /* Set up the columns for the location table */
        locationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));
        countyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCounty()));
        zipColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZip()));

        /* Add data to the table */
        locationTableView.setItems(locationData);
    }

    private void loadOfficeProviders() {
        providerImagingDrop.getItems().clear();  /* Clear any previous entries */
        providerImagingDrop.setDisable(false);   /* Enable the ComboBox */

        // Load office doctors into the providerDrop
        populateProviderDrop();
    }

    private void loadImagingProviders() {
        providerImagingDrop.getItems().clear();  /* Clear any previous entries */
        providerImagingDrop.setDisable(false);   /* Enable the ComboBox */

        /* Load radiology services into the providerDrop from the Radiology enum */
        for (Radiology service : Radiology.values()) {
            providerImagingDrop.getItems().add(service.name());  // Add the service name (e.g., "XRAY", "ULTRASOUND", "CATSCAN")
        }
    }


    /**
     * Handles the submission of an appointment when the Schedule button is clicked.
     */
    @FXML
    private void handleSchedule() {
        /* Extract the input values from the fields */
        String fName = firstName.getText().trim();
        String lName = lastName.getText().trim();
        String birthDate = (dob.getValue() != null) ? dob.getValue().toString() : null;
        String appointmentDate = (aptDate.getValue() != null) ? aptDate.getValue().toString() : null;
        String timeslot = timeslotDrop.getValue();
        String appointmentType = (office.isSelected()) ? "D" : (imaging.isSelected()) ? "T" : null;
        String providerOrImaging = providerImagingDrop.getValue();

        if (fName.isEmpty() || lName.isEmpty() || birthDate == null || appointmentDate == null || timeslot == null || providerOrImaging == null || appointmentType == null) {
            outputArea.appendText( "Please Fill Out All the Fields\n");
            return;
        }

        birthDate = formatDate(birthDate);
        appointmentDate = formatDate(appointmentDate);
        String result = "";
        switch (appointmentType) {
            case "D" -> {
                providerOrImaging = extractNPI(providerOrImaging);
                String input = String.format("%s,%s,%s,%s,%s,%s,%s",
                        appointmentType, appointmentDate, timeslot, fName, lName, birthDate, providerOrImaging);
                result = List.createAndAddOfficeAppointment(input.split(","));
                outputArea.appendText(result);
            }
            case "T" -> {
                String input = String.format("%s,%s,%s,%s,%s,%s,%s",
                        appointmentType, appointmentDate, timeslot, fName, lName, birthDate, providerOrImaging);
                result = List.scheduleTechnicianImagingAppointment(input.split(","));
                outputArea.appendText(result);
            }
            default -> outputArea.appendText("Invalid appointment type!\n");
        }

        // Process the appointment scheduling based on the appointment type


        /* Clear the input fields after scheduling */
        if (result.endsWith("booked.\n")) {
            clearFields();
            timeslotDrop.setPromptText("Timeslot");
            providerImagingDrop.setPromptText("Provider");
        }
    }
    @FXML
    private void handleCancel() {
        String fName = firstNameC.getText().trim();
        String lName = lastNameC.getText().trim();
        String birthDate = (dobC.getValue() != null) ? dobC.getValue().toString() : null;
        String appointmentDate = (aptDateC.getValue() != null) ? aptDateC.getValue().toString() : null;
        String timeslot = timeslotDropC.getValue();

        if (fName.isEmpty() || lName.isEmpty() || birthDate == null || appointmentDate == null || timeslot == null) {
            outputArea.appendText( "Please Fill Out All the Fields\n");
            return;
        }
        birthDate = formatDate(birthDate);
        appointmentDate = formatDate(appointmentDate);

        String input = String.format("C,%s,%s,%s,%s,%s", appointmentDate, timeslot, fName, lName, birthDate);
        String result = List.removeAppointment(input.split(","));
        outputArea.appendText(result);
        if (result.endsWith("canceled.\n")) {
            clearFieldsC();
        }
    }
    @FXML
    private void handleReschedule() {
        String fName = firstNameR.getText().trim();
        String lName = lastNameR.getText().trim();
        String birthDate = (dobR.getValue() != null) ? dobR.getValue().toString() : null;
        String appointmentDate = (aptDateR.getValue() != null) ? aptDateR.getValue().toString() : null;
        String timeslot1 = timeslotDropR1.getValue();
        String timeslot2 = timeslotDropR2.getValue();
        if (fName.isEmpty() || lName.isEmpty() || birthDate == null || appointmentDate == null || timeslot1 == null || timeslot2 == null ) {
            outputArea.appendText("Please Fill Out All The Fields\n");
            return;
        }
        birthDate = formatDate(birthDate);
        appointmentDate = formatDate(appointmentDate);
        String input = String.format("R,%s,%s,%s,%s,%s,%s", appointmentDate, timeslot1, fName, lName, birthDate, timeslot2);
        String result = List.rescheduleAppointment(input.split(","));
        outputArea.appendText(result);
        if (result.startsWith("Rescheduled")) {
            clearFieldsR();
        }
    }

    /**
     * Clears the input fields after submission.
     */
    @FXML
    private void clearFields() {
        firstName.clear();
        lastName.clear();
        dob.setValue(null);
        aptDate.setValue(null);
        timeslotDrop.setValue(null);
        providerImagingDrop.setValue(null);
        office.setSelected(false);
        imaging.setSelected(false);
    }
    @FXML
    private void clearFieldsC() {
        firstNameC.clear();
        lastNameC.clear();
        dobC.setValue(null);
        aptDateC.setValue(null);
        timeslotDropC.setValue(null);
    }
    @FXML
    private void clearFieldsR() {
        firstNameR.clear();
        lastNameR.clear();
        dobR.setValue(null);
        aptDateR.setValue(null);
        timeslotDropR1.setValue(null);
        timeslotDropR2.setValue(null);
    }

    @FXML
    private void handleReset() {
        clearFields();
    }
    @FXML
    private void handleResetC() {
        clearFieldsC();
    }
    @FXML
    private void handleResetR() {
        clearFieldsR();
    }

    /**
     * Handles the clearing of the output area.
     */
    @FXML
    private void handleClear() {
        outputArea.clear();
    }

    /**
     * Handles loading providers from the file.
     */
    @FXML
    private void handleLoad() {
        /* Create a FileChooser object */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Provider File");

        /* Set extension filters for .txt files */
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        /* Show the open dialog and get the selected file */
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            /* Clear previous providers */
            providerImagingDrop.getItems().clear();

            // Read the providers from the file
            String result = List.start(selectedFile.getAbsolutePath()); /* Call your method to load providers */
            outputArea.appendText(result + "\n"); /* Display the loading message in the output area */

            /* Populate the providerDrop ComboBox using the existing method */
            populateProviderDrop();

            loadProviders.setDisable(true); /* Disable the button after loading for Schedule tab */
            loadProvidersC.setDisable(true); /* Disable the button after loading for Cancel tab */
            loadProvidersR.setDisable(true); /* Disable the button after loading for Reschedule tab */
        } else {
            outputArea.appendText("File selection cancelled.\n");
        }
    }
    @FXML
    private void handlePA() {
        outputArea.appendText(List.printByAppointment());
    }
    @FXML
    private void handlePL() {
        outputArea.appendText(List.printByLocation());
    }
    @FXML
    private void handlePP() {
        outputArea.appendText(List.printByPatient());
    }
    @FXML
    private void handlePO() {
        outputArea.appendText(List.printOfficeAppointments());
    }
    @FXML
    private void handlePI() {
        outputArea.appendText(List.printImagingAppointments());
    }
    @FXML
    private void handlePS() {
        outputArea.appendText(List.displayBillingStatements());
    }
    @FXML
    private void handlePC() {
        outputArea.appendText(List.displayCreditAmounts());
    }
    private String formatDate(String date) { /* Format the date to match the methods */
        String[] parts = date.split("-");
        return parts[1] + "/" + parts[2] + "/" + parts[0];

    }
    private void populateProviderDrop() {
        /* Get provider names using the method and split by new lines */
        String providers = List.providerDrop();
        String[] providerNames = providers.split("\n");

        /* Add each provider name to the ComboBox */
        for (String name : providerNames) {
            if (!name.trim().isEmpty()) { /* Avoid adding empty lines */
                providerImagingDrop.getItems().add(name.trim());
            }
        }
    }
    private static String extractNPI(String providerInfo) {
        /* Check if the input is in the expected format */
        if (providerInfo != null && providerInfo.contains("(") && providerInfo.contains(")")) {
            /* Find the positions of the parentheses */
            int startIndex = providerInfo.indexOf('(') + 1; /* +1 to move past the '(' */
            int endIndex = providerInfo.indexOf(')');

            /* Extract the substring and parse it to an integer */
            return providerInfo.substring(startIndex, endIndex).trim();
        }
        return "-1"; /* Return -1 if npi not found. */
    }
}
