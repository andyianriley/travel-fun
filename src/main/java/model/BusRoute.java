package model;

import com.google.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class BusRoute {
    List<BusStop> stops = new ArrayList<>();

    public List<BusStop> getStops() {
        return stops;
    }

    public void setStops(List<BusStop> stops) {
        this.stops = stops;
    }

    public String getPath(){
        String mapUrl = "";//https://maps.googleapis.com/maps/api/staticmap?center=Manchester,UK&zoom=14&size=1200x400&maptype=roadmap";
        mapUrl += "&path=";

        for (BusStop busStop : getStops()) {
            mapUrl += busStop.getLatitude()+","+busStop.getLongitude()+"|";
        }

        mapUrl= mapUrl.substring(0,mapUrl.length()-2);

        return mapUrl;
    }

    public List<LatLng> getStopsLatLng(){
        List<LatLng> stops = new ArrayList<>();
        for (BusStop busStop : getStops()) {
            stops.add(new LatLng(busStop.getLatitude(),busStop.getLongitude()));
        }

        return stops;
    }
}
