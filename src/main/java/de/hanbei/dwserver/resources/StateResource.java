package de.hanbei.dwserver.resources;

import de.hanbei.dwserver.State;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/state")
public class StateResource {

    @GET
    @Path("/erraticMode")
    public Response erraticMode() {
        return erraticModeResponse();
    }

    @GET
    @Path("/erraticMode/_on")
    public Response erraticModeOn() {
        State.erraticMode(true);
        return erraticModeResponse();
    }

    @GET
    @Path("/erraticMode/_off")
    public Response erraticModeOff() {
        State.erraticMode(false);
        return erraticModeResponse();
    }

    private Response erraticModeResponse() {
        return Response.ok("{" + '"' + "erratic" + '"' + ':' + State.erraticMode() + "}").type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/wait")
    public Response maxWaitTime() {
        return maxWaitResponse();
    }

    @GET
    @Path("/wait/{maxWait}")
    public Response maxWaitTime(@PathParam("maxWait") int maxWait) {
        State.maxWait(maxWait);
        return maxWaitResponse();
    }

    private Response maxWaitResponse() {
        return Response.ok("{" + '"' + "maxWait" + '"' + ':' + State.maxWait() + "}").type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
