package sda;

import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getListOfIds() {
        Map<String, Person> personMap = Storage.getAll();

        List<String> listOfIds =  personMap
                .entrySet()
                .stream()
                .map(x -> x.getValue().getId())
                .collect(Collectors.toList());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(listOfIds);
        } catch (IOException e) {
            return "{\"error\" : \"error when getting all records\"}";
        }
    }


    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt(@PathParam("id") String id) {
        Person person = Storage.getById(id);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(person);
        } catch (IOException e) {
            return "{\"error\":\"wrong record in storage\"}";
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String postIt(Person person) {
        person.setId(String.valueOf(UUID.randomUUID()));

        Storage.put(person);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(person);
        } catch (IOException e) {
            return "{\"error\":\"wrong object to parse\"}";
        }
    }

    private List<String> mapToList(Map<String, Person> persons) {
        List<String> listOfIds = new ArrayList<>();
        for (Map.Entry<String, Person> entry : persons.entrySet()) {
            listOfIds.add(entry.getValue().getId());
        }
        return listOfIds;
    }

    private List<String> mapToList2(Map<String, Person> persons) {
        List<String> listOfIds = new ArrayList<>();
        for (String key : persons.keySet()) {
            Person person = persons.get(key);
            listOfIds.add(person.getId());
        }
        return listOfIds;
    }
}
