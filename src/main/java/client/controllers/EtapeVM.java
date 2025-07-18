package client.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

// Modèle simple d'étape (avion/hôtel) pour l’écran
class EtapeVM {
    final SimpleStringProperty type = new SimpleStringProperty();
    final SimpleStringProperty depart = new SimpleStringProperty();
    final SimpleStringProperty arrivee = new SimpleStringProperty();
    final SimpleDoubleProperty distance = new SimpleDoubleProperty(); // km
    final SimpleDoubleProperty duree = new SimpleDoubleProperty();    // h
    final SimpleDoubleProperty prix = new SimpleDoubleProperty();     // €

    EtapeVM(String type, String d, String a, double km, double h, double p) {
        this.type.set(type);
        this.depart.set(d);
        this.arrivee.set(a);
        this.distance.set(km);
        this.duree.set(h);
        this.prix.set(p);
    }
}
