import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;

public class SMS {

    public static void main(String[] args) {

        SMS sms = new SMS();
        sms.sendSMS("44number","Hi Alan");
    }


    public void sendSMS(String number, String message){
        String address = "https://api.clockworksms.com/http/send.aspx";

        Client client = Client.create();

        WebResource resource = client.resource(address);

        MultivaluedMap<String,String> multivaluedMap = new MultivaluedMapImpl();
        multivaluedMap.add("key","***********");
        multivaluedMap.add("to",number);
        multivaluedMap.add("content",message);
        multivaluedMap.add("Long","1");
        ClientResponse clientResponse = resource.queryParams(multivaluedMap).get(ClientResponse.class);

        String response = clientResponse.getEntity(String.class);

        System.out.println(response);


    }
}
