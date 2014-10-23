package model;

import com.google.maps.model.LatLng;

/**
 * Created by andy.riley on 15/10/2014.
 */
public class BusStop {

    private String AtcoCode;
    private String CommonName;
    private double Latitude;
    private double Longitude;
    private String StopType;
    private String StopSubType;
    private String TimingStatus;

    public LatLng getLocation(){
        return new LatLng(getLatitude(),getLongitude());
    }

    public String getAtcoCode() {
        return AtcoCode;
    }

    public void setAtcoCode(String atcoCode) {
        AtcoCode = atcoCode;
    }

    public String getCommonName() {
        return CommonName;
    }

    public void setCommonName(String commonName) {
        CommonName = commonName;
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

    public String getStopType() {
        return StopType;
    }

    public void setStopType(String stopType) {
        StopType = stopType;
    }

    public String getStopSubType() {
        return StopSubType;
    }

    public void setStopSubType(String stopSubType) {
        StopSubType = stopSubType;
    }

    public String getTimingStatus() {
        return TimingStatus;
    }

    public void setTimingStatus(String timingStatus) {
        TimingStatus = timingStatus;
    }
}
