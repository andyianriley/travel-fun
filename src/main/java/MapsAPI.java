import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.*;
import model.DistanceInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsAPI {

    private GeoApiContext context;


    public static void main(String[] args) {

        MapsAPI mapsAPI = new MapsAPI();
        LatLng bl99EL = mapsAPI.geo("BL99EL");
        LatLng m456AN = mapsAPI.geo("M456AN");
        mapsAPI.directions("BL99EL", "M456AN");
        mapsAPI.directions(bl99EL, m456AN);
    }

    public MapsAPI() {
        this.context = new GeoApiContext().setApiKey("*********");
    }

    public LatLng geo(String address){
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    address).await();
            System.out.println(results[0].formattedAddress);
            return results[0].geometry.location;
        }catch (Exception e){
            throw new RuntimeException("error geo",e);
        }
    }

    public DistanceInfo distance(LatLng origin, List<LatLng> positions){
        try {

            DistanceMatrix results = DistanceMatrixApi.newRequest(context).mode(TravelMode.WALKING).origins(origin).destinations(positions.toArray(new LatLng[positions.size()])).await();

            DistanceInfo distanceInfo = smallestDistance(results.rows, positions , origin);


            return distanceInfo;

        }catch (Exception e){
            throw new RuntimeException("error directions",e);
        }
    }

    private DistanceInfo smallestDistance(DistanceMatrixRow[] rows, List<LatLng> positions, LatLng origin) {
        DistanceInfo distanceInfo = new DistanceInfo();
        distanceInfo.setDistance(Double.MAX_VALUE);
        int i =0;
        for (DistanceMatrixRow row : rows) {
            //System.out.println("rows:"+row.elements.length);
            for (DistanceMatrixElement element : row.elements) {
                //System.out.println(String.format("dist: %s %s",element.distance,element.duration));
                Distance distance = element.distance;
                if(distance.inMeters < distanceInfo.getDistance()){
                    distanceInfo.setDistance(distance.inMeters);
                    distanceInfo.setDuration(element.duration.humanReadable);
                    distanceInfo.setDestination(positions.get(i));
                }
                i++;
            }

        }
        distanceInfo.setOrigin(origin);
        distanceInfo.setWalkingPath(directionsWalking(distanceInfo.getOrigin(),distanceInfo.getDestination()));
        return distanceInfo;
    }

    public List<LatLng> directionsWalking(LatLng address1, LatLng address2){
        try {
            DirectionsRoute[] routes = DirectionsApi.newRequest(context).origin(address1).destination(address2).mode(TravelMode.WALKING).await();

            return getRoute(routes);


        }catch (Exception e){
            throw new RuntimeException("error directions",e);
        }
    }
    public void directions(String address1, String address2){
        try {
            DirectionsRoute[] routes = DirectionsApi.getDirections(context, address1, address2).mode(TravelMode.DRIVING).await();

            outputRoutes(routes);


        }catch (Exception e){
            throw new RuntimeException("error directions",e);
        }
    }
    public void directions(LatLng address1, LatLng address2){
        try {
            DirectionsRoute[] routes = DirectionsApi.newRequest(context).origin(address1).destination(address2).mode(TravelMode.DRIVING).await();

            outputRoutes(routes);


        }catch (Exception e){
            throw new RuntimeException("error directions",e);
        }
    }

    private void outputRoutes(DirectionsRoute[] routes) {
        for (DirectionsRoute route : routes) {
            System.out.println(route.summary);
            for (DirectionsLeg leg : route.legs) {
                System.out.println(String.format("legs %s %s Duration: %s Traffic: %s Distance: %s",leg.startAddress,leg.endAddress,leg.duration,leg.durationInTraffic,leg.distance));

                if(leg.steps != null) {
                    for (DirectionsStep step : leg.steps) {
                        System.out.println(String.format("step %s %s %s %s", step.startLocation, step.endLocation, step.distance, step.duration, step.travelMode.name()));

                        if (step.subSteps != null) {
                            for (DirectionsStep subStep : step.subSteps) {
                                System.out.println(String.format("sub-step %s %s %s %s", subStep.startLocation, subStep.endLocation, subStep.distance, subStep.duration, subStep.travelMode.name()));

                            }
                        }

                    }
                }

            }

        }
    }

    private List<LatLng> getRoute(DirectionsRoute[] routes) {
        for (DirectionsRoute route : routes) {
            System.out.println(route.summary);
            List<LatLng> points = new ArrayList<LatLng>();
            for (DirectionsLeg leg : route.legs) {
                System.out.println(String.format("legs %s %s Duration: %s Traffic: %s Distance: %s",leg.startAddress,leg.endAddress,leg.duration,leg.durationInTraffic,leg.distance));

                if(leg.steps != null) {
                    for (DirectionsStep step : leg.steps) {
                        System.out.println(String.format("step %s %s %s %s", step.startLocation, step.endLocation, step.distance, step.duration, step.travelMode.name()));

                        points.add(step.startLocation);

                    }
                }

            }
            return points;
        }
        return null;
    }
}
