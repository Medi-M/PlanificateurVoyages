package client.common.net;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class NetworkClient {
    private final String host;
    private final int port;
    private final ExecutorService io = Executors.newCachedThreadPool();

    public NetworkClient(String host, int port) {
        this.host = host; this.port = port;
    }

    private interface LineHandler { void handle(BufferedReader in, PrintWriter out) throws Exception; }

    private void session(LineHandler op) throws Exception {
        try (Socket s = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true)) {
            op.handle(in, out);
        }
    }

    public record CityDTO(int id, String name, String country, double lat, double lon, double hotelPrice){}

    public void listCities(ConsumerUI<List<CityDTO>> callback) {
        io.submit(() -> {
            try {
                List<CityDTO> list = new ArrayList<>();
                session((in, out) -> {
                    out.println("LIST_CITIES");
                    String line = in.readLine();
                    if (line == null || !line.startsWith("OK")) throw new IOException("bad response");
                    while ((line = in.readLine()) != null) {
                        if (line.equals("END")) break;
                        String[] p = line.split(";");
                        list.add(new CityDTO(
                                Integer.parseInt(p[0]), p[1], p[2],
                                Double.parseDouble(p[3]), Double.parseDouble(p[4]),
                                Double.parseDouble(p[5])
                        ));
                    }
                });
                Platform.runLater(() -> callback.accept(list));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> callback.accept(new ArrayList<>()));
            }
        });
    }

    public void getDistance(int idA, int idB, ConsumerUI<Double> callback) {
        io.submit(() -> {
            try {
                final double[] val = {0.0};
                session((in,out) -> {
                    out.println("DIST " + idA + " " + idB);
                    String resp = in.readLine();
                    if (resp != null && resp.startsWith("OK ")) {
                        val[0] = Double.parseDouble(resp.substring(3).trim());
                    } else throw new IOException("bad response");
                });
                Platform.runLater(() -> callback.accept(val[0]));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> callback.accept(0.0));
            }
        });
    }

    public void getHotelPrice(int cityId, ConsumerUI<Double> callback) {
        io.submit(() -> {
            try {
                final double[] price = {0.0};
                session((in,out)->{
                    out.println("HOTEL_PRICE " + cityId);
                    String resp = in.readLine();
                    if (resp != null && resp.startsWith("OK ")) {
                        price[0] = Double.parseDouble(resp.substring(3).trim());
                    } else throw new IOException("bad response");
                });
                Platform.runLater(() -> callback.accept(price[0]));
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> callback.accept(0.0));
            }
        });
    }

    @FunctionalInterface
    public interface ConsumerUI<T> { void accept(T t); }
}
