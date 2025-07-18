package client.common;

public class EtapeAvion extends Etape {
    public EtapeAvion(String villeDepart, String villeArrivee, double distanceKm, double dureeHeures, double prix) {
        super("AVION", villeDepart, villeArrivee, distanceKm, dureeHeures, prix);
    }
}
