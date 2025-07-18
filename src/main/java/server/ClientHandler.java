/*package server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final CityRepo repo = new CityRepo();

    public ClientHandler(Socket s) { this.socket = s; }

    @Override public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                try {
                    // Protocole très simple, commands séparées par espaces
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length == 0) continue;
                    String cmd = parts[0].toUpperCase();

                    switch (cmd) {
                        case "PING" -> out.println("PONG");
                        case "LIST_CITIES" -> {
                            List<CityRepo.City> cities = repo.listAll();
                            out.println("OK " + cities.size());
                            for (CityRepo.City c : cities) {
                                // id;name;country;lat;lon;hotelPrice
                                out.printf("%d;%s;%s;%.6f;%.6f;%.2f%n",
                                        c.id(), c.name(), c.country(), c.lat(), c.lon(), c.hotelPrice());
                            }
                            out.println("END");
                        }
                        case "DIST" -> {
                            if (parts.length != 3) { out.println("ERR bad_args"); break; }
                            int a = Integer.parseInt(parts[1]);
                            int b = Integer.parseInt(parts[2]);
                            var ca = repo.byId(a);
                            var cb = repo.byId(b);
                            if (ca == null || cb == null) { out.println("ERR not_found"); break; }
                            double d = Geo.haversine(ca.lat(), ca.lon(), cb.lat(), cb.lon());
                            out.printf("OK %.3f%n", d);
                        }
                        case "HOTEL_PRICE" -> {
                            if (parts.length != 2) { out.println("ERR bad_args"); break; }
                            int id = Integer.parseInt(parts[1]);
                            var c = repo.byId(id);
                            if (c == null) { out.println("ERR not_found"); break; }
                            out.printf("OK %.2f%n", c.hotelPrice());
                        }
                        default -> out.println("ERR unknown_cmd");
                    }
                } catch (SQLException | RuntimeException ex) {
                    ex.printStackTrace();
                    out.println("ERR server");
                }
            }
        } catch (IOException ignore) {
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}*/
