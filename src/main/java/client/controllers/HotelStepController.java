package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

public class HotelStepController {

    private static final double PRIX_PAR_NUIT_PAR_CHAMBRE = 100.0; // tu peux changer

    @FXML private Spinner<Integer> chambresSpinner;
    @FXML private Spinner<Integer> nuitsSpinner;
    @FXML private Label titreLabel;
    @FXML private Label resumeLabel;
    @FXML private GridPane contentArea;

    private Runnable onRemove = () -> {};
    private Runnable onChanged = () -> {};

    @FXML
    public void initialize() {
        // Spinners numériques avec min/max
        chambresSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1));
        nuitsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 365, 1));
        chambresSpinner.setEditable(true);
        nuitsSpinner.setEditable(true);

        // Saisie libre au clavier + clamp entre min/max
        chambresSpinner.getEditor().setTextFormatter(intFormatter(chambresSpinner));
        nuitsSpinner.getEditor().setTextFormatter(intFormatter(nuitsSpinner));

        // Listeners -> recalcul automatique
        chambresSpinner.valueProperty().addListener((o, a, b) -> recalc());
        nuitsSpinner.valueProperty().addListener((o, a, b) -> recalc());

        recalc();
    }

    /** TextFormatter qui borne la valeur saisie entre min et max du Spinner. */
    private TextFormatter<Integer> intFormatter(Spinner<Integer> sp) {
        // On sait qu'on a mis une IntegerSpinnerValueFactory juste au-dessus
        SpinnerValueFactory.IntegerSpinnerValueFactory vf =
                (SpinnerValueFactory.IntegerSpinnerValueFactory) sp.getValueFactory();

        return new TextFormatter<>(new StringConverter<Integer>() {
            @Override public String toString(Integer v) {
                return v == null ? "" : v.toString();
            }
            @Override public Integer fromString(String s) {
                try {
                    int val = Integer.parseInt(s.trim());
                    // clamp entre min et max
                    val = Math.max(vf.getMin(), Math.min(val, vf.getMax()));
                    return val;
                } catch (Exception e) {
                    // si saisie invalide, on garde la valeur actuelle
                    return sp.getValue();
                }
            }
        });
    }

    @FXML
    private void toggleCollapsed() {
        boolean visible = contentArea.isVisible();
        contentArea.setVisible(!visible);
        contentArea.setManaged(!visible);
    }

    @FXML
    private void removeSelf() {
        onRemove.run();
    }

    public void setOnRemove(Runnable r) { this.onRemove = (r != null) ? r : () -> {}; }
    public void setOnChanged(Runnable r) { this.onChanged = (r != null) ? r : () -> {}; }

    private void recalc() {
        int ch = chambresSpinner.getValue();
        int nuits = nuitsSpinner.getValue();
        double total = ch * nuits * PRIX_PAR_NUIT_PAR_CHAMBRE;

        String titre = String.format("%d chambre(s), %d nuit(s) — %.2f €", ch, nuits, total);
        titreLabel.setText(titre);
        resumeLabel.setText(String.format("%d chambre(s) × %d nuit(s) = %.2f €", ch, nuits, total));

        onChanged.run();
    }

    public double getCoutTotal() {
        return chambresSpinner.getValue() * nuitsSpinner.getValue() * PRIX_PAR_NUIT_PAR_CHAMBRE;
    }
}
