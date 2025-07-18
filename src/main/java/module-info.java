module be.groupe5.planificateurvoyages {
    requires javafx.controls;
    requires javafx.fxml;

    // ... tes exports existants
    exports client.controllers;
    exports client.views;      // si tu en as un
    exports client.common;     // si tu veux aussi l'exporter

    // OUVERTURE pour PropertyValueFactory (réflexion)
    opens client.controllers to javafx.fxml;
    opens client.common     to javafx.base;  // <-- important pour TableView
    opens client.views      to javafx.fxml;  // si besoin
}
