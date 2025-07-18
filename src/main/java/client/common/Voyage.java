package client.common;

import java.time.LocalDate;

public class Voyage {
    private final String nom;
    private final String destination;
    private final LocalDate dateDepart;
    private final LocalDate dateRetour;

    private double distanceTotale;
    private double dureeTotale;
    private double prixTotal;

    public Voyage(String nom, String destination, LocalDate dateDepart, LocalDate dateRetour) {
        this.nom = nom;
        this.destination = destination;
        this.dateDepart = dateDepart;
        this.dateRetour = dateRetour;
    }

    public String getNom() { return nom; }
    public String getDestination() { return destination; }
    public LocalDate getDateDepart() { return dateDepart; }
    public LocalDate getDateRetour() { return dateRetour; }

    public double getDistanceTotale() { return distanceTotale; }
    public double getDureeTotale()    { return dureeTotale; }
    public double getPrixTotal()      { return prixTotal; }

    public void setTotaux(double distance, double duree, double prix) {
        this.distanceTotale = distance;
        this.dureeTotale = duree;
        this.prixTotal = prix;
    }
}
