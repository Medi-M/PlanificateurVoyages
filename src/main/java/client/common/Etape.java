package client.common;

public class Etape {
    private final String type;      // "AVION", "HOTEL", etc. (optionnel)
    private final String depart;    // optionnel
    private final String arrivee;   // optionnel
    private final double distance;  // en km
    private final double duree;     // en heures
    private final double prix;      // en €

    public Etape(String type, String depart, String arrivee, double distance, double duree, double prix) {
        this.type = type;
        this.depart = depart;
        this.arrivee = arrivee;
        this.distance = distance;
        this.duree = duree;
        this.prix = prix;
    }

    public String getType()     { return type; }
    public String getDepart()   { return depart; }
    public String getArrivee()  { return arrivee; }
    public double getDistance() { return distance; }
    public double getDuree()    { return duree; }
    public double getPrix()     { return prix; }
}
