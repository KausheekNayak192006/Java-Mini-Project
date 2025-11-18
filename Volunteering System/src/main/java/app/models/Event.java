package app.models;

import java.time.LocalDate;

public class Event {
    private int id;
    private String name;
    private LocalDate date;
    private String location;
    private String volunteer; // userid of volunteer (nullable)
    private String ngoUserid; // creator

    public Event(int id, String name, LocalDate date, String location, String volunteer, String ngoUserid) {
        this.id = id; this.name = name; this.date = date; this.location = location; this.volunteer = volunteer; this.ngoUserid = ngoUserid;
    }
    public Event(String name, LocalDate date, String location, String ngoUserid) {
        this(0, name, date, location, null, ngoUserid);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public LocalDate getDate() { return date; }
    public String getLocation() { return location; }
    public String getVolunteer() { return volunteer; }
    public String getNgoUserid() { return ngoUserid; }
    public void setVolunteer(String v) { this.volunteer = v; }
}