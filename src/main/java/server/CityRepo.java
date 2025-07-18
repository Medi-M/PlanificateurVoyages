/*package server;

import java.sql.*;
import java.util.*;

public class CityRepo {
    public static record City(int id, String name, String country, double lat, double lon, double hotelPrice){}

    public List<City> listAll() throws SQLException {
        String sql = """
            SELECT c.id, c.name, co.name AS country, c.lat, c.lon, c.hotel_price_per_night
            FROM city c JOIN country co ON co.id = c.country_id
            ORDER BY co.name, c.name
        """;
        try (Connection cn = Db.get();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<City> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getDouble("lat"),
                        rs.getDouble("lon"),
                        rs.getDouble("hotel_price_per_night")
                ));
            }
            return list;
        }
    }

    public City byId(int id) throws SQLException {
        String sql = """
            SELECT c.id, c.name, co.name AS country, c.lat, c.lon, c.hotel_price_per_night
            FROM city c JOIN country co ON co.id = c.country_id
            WHERE c.id = ?
        """;
        try (Connection cn = Db.get();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getDouble("lat"),
                        rs.getDouble("lon"),
                        rs.getDouble("hotel_price_per_night")
                );
            }
        }
    }
}*/
