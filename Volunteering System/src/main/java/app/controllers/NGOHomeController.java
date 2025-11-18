package app.controllers;

import app.dao.EventDao;
import app.models.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.Label;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;


public class NGOHomeController {
    @FXML
    private TextField tfEventName, tfLocation;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TableView<Event> table;
    @FXML
    private TableColumn<Event, String> colName, colLoc, colVolunteer, colDate;
    @FXML
    private Label lblHeader, lblMsg;

    private final EventDao dao = new EventDao();
    private String ngoUserid;
    private final ObservableList<Event> data = FXCollections.observableArrayList();

    public void setContext(String ngoUserid) {
        this.ngoUserid = ngoUserid;
        lblHeader.setText("NGO Home – " + ngoUserid);
        refresh();
    }

    @FXML
    public void initialize() {
        dpDate.setValue(LocalDate.now());
        colName.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        colLoc.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLocation()));
        colVolunteer.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getVolunteer() == null ? "(unbooked)" : c.getValue().getVolunteer()));
        colDate.setCellValueFactory(
                c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDate().toString()));
        table.setItems(data);
    }

    @FXML
    public void addEvent() {
        try {
            Event e = new Event(tfEventName.getText().trim(), dpDate.getValue(), tfLocation.getText().trim(),
                    ngoUserid);
            if (dao.insert(e)) {
                lblMsg.setText("Event added successfully!");
                clearForm();
                refresh();
            }
        } catch (Exception ex) {
            lblMsg.setText("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void clearForm() {
        tfEventName.clear();
        tfLocation.clear();
        dpDate.setValue(LocalDate.now());
    }

    private void refresh() {
        try {
            data.setAll(dao.listAll());
        } catch (SQLException e) {
            lblMsg.setText("Error loading events: " + e.getMessage());
        }
    }

    @FXML
    public void onLogout() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) table.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}