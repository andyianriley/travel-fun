package model;

import com.google.maps.model.LatLng;

import java.util.List;

public class DistanceInfo {

    private LatLng origin;
    private LatLng destination;
    private double distance;
    private String duration;
    private List<LatLng> walkingPath;

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPath(){
        return String.format("&path=color:red|%s,%s|%s,%s",origin.lat,origin.lng,destination.lat,destination.lng);
    }

    public void setWalkingPath(List<LatLng> walkingPath) {
        this.walkingPath = walkingPath;
    }

    public String getWalkingPath(){

        String uri = "&path=color:red";

        for (LatLng latLng : walkingPath) {
            uri += String.format("|%s,%s",latLng.lat,latLng.lng);
        }

        return uri;
    }
}
