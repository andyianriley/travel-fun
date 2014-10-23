package model;

import com.google.maps.model.LatLng;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Bus {
    private int Id;
    private String Route;
    private String Registration;
    private String Heading;
    private boolean HasFix;
    private boolean IsParked;
    private String LastUpdated;
    private double Latitude;
    private double Longitude;

    public LatLng getLocation(){
        return new LatLng(getLatitude(),getLongitude());
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
    }

    public String getRegistration() {
        return Registration;
    }

    public void setRegistration(String registration) {
        Registration = registration;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public boolean isHasFix() {
        return HasFix;
    }

    public void setHasFix(boolean hasFix) {
        HasFix = hasFix;
    }

    public boolean isParked() {
        return IsParked;
    }

    public void setParked(boolean isParked) {
        IsParked = isParked;
    }

    public String getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        LastUpdated = lastUpdated;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
