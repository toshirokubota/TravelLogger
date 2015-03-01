package edu.susqu.math.kubota.travellogger;

import java.util.Scanner;

/**
 * Created by kubota on 2/16/2015.
 */
public class Location {
    private String longitude;
    private String lattitude;
    private String city;
    private String state;
    private String country;

    public Location(String longitude, String lattitude) {
        this.longitude = longitude;
        this.lattitude = lattitude;
    }
    public Location(String longitude, String lattitude,
                    String city, String state, String country){
        this(longitude, lattitude);
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return longitude + " " + lattitude + " " + city + " " + state + " " + country;
    }

    public static Location fromString(String s) {
        Scanner scanner = new Scanner(s);
        String s1 = scanner.hasNext() ? scanner.next(): "NA";
        String s2 = scanner.hasNext() ? scanner.next(): "NA";
        String s3 = scanner.hasNext() ? scanner.next(): "NA";
        String s4 = scanner.hasNext() ? scanner.next(): "NA";
        String s5 = scanner.hasNext() ? scanner.next(): "NA";
        return new Location(s1, s2, s3, s4, s5);
    }
}
