package client.controllers;

import client.common.DistanceUtils;
import client.common.Voyage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CreationVoyageController {

    @FXML private TextField        nomVoyageField;
    @FXML private ComboBox<String> villeDepartCombo;
    @FXML private ComboBox<String> destinationCombo;
    @FXML private DatePicker       dateDebutPicker;
    @FXML private ToggleGroup      vitesseToggle;   // défini dans <fx:define> du FXML
    @FXML private Spinner<Integer> attenteSpinner;
    @FXML private ComboBox<Double> prixKmCombo;
    @FXML private Label            infosVoyageLabel; // résumé en haut
    @FXML private Label            totauxLabel;      // résumé en bas

    @FXML private VBox stepsContainer;

    private final List<HotelStepController> hotelSteps = new ArrayList<>();

    private Consumer<Voyage> voyageCallback;
    public void setVoyageCallback(Consumer<Voyage> cb) { this.voyageCallback = cb; }

    // -------------------- Initialisation --------------------
    @FXML
    public void initialize() {
        villeDepartCombo.getItems().setAll("Bruxelles", "Paris", "Londres", "Rome");
        destinationCombo.getItems().setAll("New York", "Tokyo", "Madrid", "Londres");

        prixKmCombo.getItems().setAll(0.05, 0.10, 0.15);
        prixKmCombo.getSelectionModel().select(1); // 0.10 €/km

        attenteSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 240, 60, 5)
        );

        dateDebutPicker.setValue(LocalDate.now());
        setTotauxText(0, 0, 0);

        villeDepartCombo.setOnAction(e -> majResume());
        destinationCombo.setOnAction(e -> majResume());
        prixKmCombo.setOnAction(e -> majResume());
        attenteSpinner.valueProperty().addListener((obs, o, n) -> majResume());
        if (vitesseToggle != null) {
            vitesseToggle.selectedToggleProperty().addListener((obs, o, n) -> majResume());
        }
    }

    @FXML
    private void ajouterEtapeAvion() {
        // placeholder pour plus tard
        new Alert(Alert.AlertType.INFORMATION, "Formulaire avion à venir 😉").showAndWait();
    }

    @FXML
    public void ajouterEtapeHotel() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/controllers/hotel-step.fxml")); // <-- CHEMIN CORRECT
            Node hotelStep = loader.load();

            HotelStepController ctrl = loader.getController();
            ctrl.setOnRemove(() -> stepsContainer.getChildren().remove(hotelStep));
            ctrl.setOnChanged(this::majResume);

            hotelStep.getProperties().put("controller", ctrl);

            stepsContainer.getChildren().add(hotelStep);
            majResume();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Impossible de charger la vue hôtel.").showAndWait();
        }
    }

    @FXML
    private void validerVoyage() {
        String titre = nomVoyageField.getText();
        if (titre == null || titre.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Donne un nom à ton voyage 🙂").showAndWait();
            return;
        }
        if (destinationCombo.getValue() == null || dateDebutPicker.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Choisis une destination et une date.").showAndWait();
            return;
        }

        LocalDate debut  = dateDebutPicker.getValue();
        LocalDate retour = debut.plusDays(7); // provisoire

        Voyage v = new Voyage(titre, destinationCombo.getValue(), debut, retour);

        Calcul base = calculer();
        double hotels = hotelSteps.stream().mapToDouble(HotelStepController::getCoutTotal).sum();
        v.setTotaux(base.distance, base.duree, base.prix + hotels);

        if (voyageCallback != null) voyageCallback.accept(v);
        ((Stage) nomVoyageField.getScene().getWindow()).close();
    }

    private void majResume() {
        Calcul c = calculer();
        double hotels = hotelSteps.stream().mapToDouble(HotelStepController::getCoutTotal).sum();
        setTotauxText(c.distance, c.duree, c.prix + hotels);
    }

    private void setTotauxText(double km, double h, double euros) {
        String txt = String.format("%.2f km, %.2f heures, %.2f euros", km, h, euros);
        if (infosVoyageLabel != null) infosVoyageLabel.setText(txt);
        if (totauxLabel != null)      totauxLabel.setText(txt);
    }

    private Calcul calculer() {
        String  dep        = villeDepartCombo.getValue();
        String  arr        = destinationCombo.getValue();
        Double  prixKm     = prixKmCombo.getValue();
        Integer attenteMin = attenteSpinner.getValue();
        Toggle  t          = (vitesseToggle != null) ? vitesseToggle.getSelectedToggle() : null;

        if (dep == null || arr == null || prixKm == null || attenteMin == null || t == null) {
            return new Calcul(0, 0, 0);
        }

        int vitesse   = Integer.parseInt(String.valueOf(t.getUserData())); // 600/800/900
        double dist   = DistanceUtils.getDistanceBetween(dep, arr);        // km
        double duree  = dist / vitesse + (attenteMin / 60.0);              // h
        double prix   = dist * prixKm;                                     // €

        return new Calcul(dist, duree, prix);
    }

    private record Calcul(double distance, double duree, double prix) {}
}
