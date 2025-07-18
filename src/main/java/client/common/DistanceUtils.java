package client.common;

import java.util.Map;

public final class DistanceUtils {
    private DistanceUtils() {}

    private static final Map<String, Double> DIST = Map.of(
            "Bruxelles:New York", 5884.11,
            "Bruxelles:Tokyo",    9713.00,
            "Paris:New York",     5843.00,
            "Paris:Tokyo",        9715.00,
            "Londres:New York",   5567.00,
            "Londres:Tokyo",      9558.00,
            "Rome:New York",      6881.00,
            "Rome:Tokyo",         9850.00
    );

    public static double getDistanceBetween(String depart, String arrivee) {
        if (depart == null || arrivee == null) return 0.0;
        Double v = DIST.get(depart + ":" + arrivee);
        if (v != null) return v;
        // fallback bidon si non trouvé
        return 3000.0;
    }
}
