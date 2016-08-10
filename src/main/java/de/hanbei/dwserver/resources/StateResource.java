package de.hanbei.dwserver.resources;

import de.hanbei.dwserver.State;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/state")
public class StateResource {

    @GET
    @Path("/erraticMode")
    public Response erraticMode() {
        return response();
    }

    @GET
    @Path("/erraticMode/_on")
    public Response erraticModeOn() {
        State.erraticMode(true);
        return response();
    }

    @GET
    @Path("/erraticMode/_off")
    public Response erraticModeOff() {
        State.erraticMode(false);
        return response();
    }

    private Response response() {
        return Response.ok("{" + '"' + "erratic" + '"' + ':' + State.erraticMode() + "}").type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
