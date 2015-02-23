package edu.susqu.math.kubota.travellogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by kubota on 2/2/2015.
 */
public class Trip {
    private String name;
    private Date date;
    private UUID id;
    private Location location;
    private String description;

    private Trip() {
    }

    public static Trip creteTrip(String name, Location location){
        Trip trip = new Trip();
        trip.name = name;
        trip.date = new Date();
        trip.id = UUID.randomUUID();
        trip.location = location;
        trip.description = "NA";
        return trip;
    }

    public static Trip recoverTrip(String name, String uuid, String desc, String date, Location location)
    throws ParseException {
        DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Trip trip = new Trip();
        trip.name = name;
        trip.date = format.parse(date);
        trip.id = UUID.fromString(uuid);
        trip.location = location;
        trip.description = desc;
        return trip;
    }


    public String getDescription() {
        return description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Trip for " + name + " on " + date + " #" + id;
    }
}
