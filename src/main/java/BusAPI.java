import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.model.LatLng;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import model.Bus;
import model.BusRoute;
import model.BusStop;
import model.DistanceInfo;

import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BusAPI {


    public static void main(String[] args) {

        BusAPI mapsAPI = new BusAPI();
        //mapsAPI.busRoute(1);
//        mapsAPI.bus(1);
        mapsAPI.bus(3);
//        mapsAPI.bus(3);
//        mapsAPI.bus(4);
    }

    public BusRoute busRoute(int route){
        String address = "http://opendata.tfgm.com/";
        String uri = String.format("api/routes/%s/stops",route);

        Client client = Client.create();

        WebResource resource = client.resource(address+uri);

        ClientResponse clientResponse = resource
                .header("DevKey","************")
                .header("AppKey","*************")
                .accept(new MediaType("text","json")).get(ClientResponse.class);


        String response = clientResponse.getEntity(String.class);

        System.out.println(response);

        Type collectionType = new TypeToken<List<BusStop>>(){}.getType();
        List<BusStop> busStops = new Gson().fromJson(response,collectionType);

        BusRoute busRoute = new BusRoute();
        busRoute.setStops(busStops);

        return busRoute;

    }

    public void bus(int route){
        String address = "http://opendata.tfgm.com/";
//        String uri = "api/enums";
       String uri = String.format("api/routes/%s/buses",route);

        Client client = Client.create();

        WebResource resource = client.resource(address+uri);

        ClientResponse clientResponse = resource
                .header("DevKey","fac43163-22e1-49ca-a412-f09b8f32e621")
                .header("AppKey","5ded61ed-92f2-4fab-a36a-cf589d9e5db2")
                .accept(new MediaType("text","json")).get(ClientResponse.class);


        String response = clientResponse.getEntity(String.class);


        Type collectionType = new TypeToken<List<Bus>>(){}.getType();
        List<Bus> buses = new Gson().fromJson(response,collectionType);
        List<LatLng> busPos = new ArrayList<>();

        int i = 1;
        String mapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=Manchester,UK&zoom=14&size=1200x400&maptype=roadmap";
        for (Bus bus : buses) {
            System.out.println(bus.toString());
            busPos.add(bus.getLocation());
            mapUrl += "&markers=label:"+(i++)+"%7C"+bus.getLatitude()+","+bus.getLongitude();
        }

        if(busPos.size() > 0) {

            MapsAPI mapsAPI = new MapsAPI();
            LatLng geo = mapsAPI.geo("First Street, Manchester, UK");

            mapUrl += "&markers=color:blue%7Clabel:0%7C"+geo.lat+","+geo.lng;

            BusRoute busRoute = busRoute(route);
            mapUrl += busRoute.getPath();

            DistanceInfo nearestStop = mapsAPI.distance(geo, busRoute.getStopsLatLng());

            mapUrl += nearestStop.getWalkingPath();

            System.out.println("walking to stop:"+nearestStop.getDuration());
            System.out.println(mapUrl);
        }




        /*
        https://maps.googleapis.com/maps/api/staticmap?center=Manchester,UK&zoom=13&size=600x300&maptype=roadmap&markers=color:blue%7Clabel:S%7C53.4809799194336,-2.24224996566772&markers=color:green%7Clabel:G%7C53.4696502685547,-2.31078004837036&markers=color:red%7Clabel:C%7C53.472974,-2.247227

https://maps.googleapis.com/maps/api/staticmap?center=Manchester,UK&zoom=14&size=1200x400&maptype=roadmap
&markers=label:1%7C53.4809799194336,-2.24224996566772
&markers=label:2%7C53.4781494140625,-2.23286008834839
&markers=label:3%7C53.4808082580566,-2.24201011657715
&markers=color:blue%7Clabel:0%7C53.472974,-2.247227
*/
    }

}
