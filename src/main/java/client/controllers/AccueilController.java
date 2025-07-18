package client.controllers;

import client.common.Voyage;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AccueilController {

    @FXML private TableView<Voyage> tableVoyages;
    @FXML private TableColumn<Voyage, String> nomCol;
    @FXML private TableColumn<Voyage, String> destinationCol;
    @FXML private TableColumn<Voyage, LocalDate> dateDepartCol;
    @FXML private TableColumn<Voyage, LocalDate> dateRetourCol;
    @FXML private TableColumn<Voyage, Double> distanceCol;
    @FXML private TableColumn<Voyage, Double> dureeCol;
    @FXML private TableColumn<Voyage, Double> prixCol;
    @FXML private Button modifierButton;
    @FXML private Button supprimerButton;

    private final ObservableList<Voyage> voyages = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nomCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNom()));
        destinationCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDestination()));
        dateDepartCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getDateDepart()));
        dateRetourCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().getDateRetour()));
        distanceCol.setCellValueFactory(new PropertyValueFactory<>("distanceTotale"));
        dureeCol.setCellValueFactory(new PropertyValueFactory<>("dureeTotale"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));

        tableVoyages.setItems(voyages);

        if (modifierButton != null) modifierButton.setOnAction(e -> {/* TODO */});
        if (supprimerButton != null) supprimerButton.setOnAction(e -> {
            var sel = tableVoyages.getSelectionModel().getSelectedItem();
            if (sel != null) voyages.remove(sel);
        });
    }

    @FXML
    public void handleCreerVoyage(ActionEvent event) {
        try {
            var url = getClass().getResource("/client/views/creation-voyage-detail.fxml");
            if (url == null) {
                new Alert(Alert.AlertType.ERROR, "FXML introuvable: /client/views/creation-voyage-detail.fxml").showAndWait();
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            CreationVoyageController ctrl = loader.getController();
            ctrl.setVoyageCallback(voyage -> {
                // pas besoin d’appeler recalculerTotaux ici si CreationVoyageController a déjà setTotaux
                voyages.add(voyage);
            });

            Stage dialog = new Stage();
            dialog.setTitle("Définir son voyage");
            dialog.initOwner(tableVoyages.getScene().getWindow());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(root, 900, 600));
            dialog.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Impossible d’ouvrir la page de création.").showAndWait();
        }
    }

}
