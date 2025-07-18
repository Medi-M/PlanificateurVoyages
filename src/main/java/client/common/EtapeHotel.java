package client.common;

public class EtapeHotel extends Etape {
    public EtapeHotel(String ville, int nuits, double prixParNuit) {
        super("HOTEL", ville, null, 0.0, 0.0, nuits * prixParNuit);
    }
}
