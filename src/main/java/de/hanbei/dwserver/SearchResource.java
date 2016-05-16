package de.hanbei.dwserver;

import de.hanbei.model.StaticResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

@Path("/search")
public class SearchResource {

    private Random random;

    public SearchResource() {
        this.random = new Random();
    }

    @Path("/{searcher}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response respond(@PathParam("searcher") String keyword) {
        try {
            Thread.sleep(random.nextInt(2000));
        } catch (InterruptedException e) {
            throw new WebApplicationException(e);
        }
        return Response.ok(StaticResponse.data).build();
    }
}
