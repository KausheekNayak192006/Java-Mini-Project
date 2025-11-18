 package app.dao; 
import app.Database;
import app.models.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // <-- we want this one
import java.util.ArrayList;
import java.util.List;


public class EventDao {
    public boolean insert(Event e) throws SQLException {
        String sql = "INSERT INTO events (event_name, date, location, ngo_userid) VALUES (?,?,?,?)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, e.getName());
            ps.setDate(2, Date.valueOf(e.getDate()));
            ps.setString(3, e.getLocation());
            ps.setString(4, e.getNgoUserid());
            return ps.executeUpdate() == 1;
        }
    }

    public List<Event> listAll() throws SQLException {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT id, event_name, date, location, volunteer, ngo_userid FROM events ORDER BY date";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Event(
                    rs.getInt("id"),
                    rs.getString("event_name"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("location"),
                    rs.getString("volunteer"),
                    rs.getString("ngo_userid")));
            }
        }
        return list;
    }

    public boolean bookEvent(int eventId, String volunteerUserid) throws SQLException {
        String sql = "UPDATE events SET volunteer=? WHERE id=? AND volunteer IS NULL";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, volunteerUserid);
            ps.setInt(2, eventId);
            return ps.executeUpdate() == 1; // success only if not already booked
        }
    }
}