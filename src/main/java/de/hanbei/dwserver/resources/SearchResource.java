package de.hanbei.dwserver.resources;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.Uninterruptibles;
import de.hanbei.dwserver.State;
import de.hanbei.dwserver.auth.User;
import io.dropwizard.auth.Auth;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.google.common.io.Resources.getResource;

@Path("/search")
public class SearchResource {

    private Random random;

    public SearchResource() {
        this.random = new Random();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response respond(@QueryParam("q") String query, @QueryParam("size") int size) {
        return getSearcherResponse("fred", "br");
    }

    @Path("/{searcher}")
    @GET
    public Response respondAsSearcher(@Auth User user,
                                      @PathParam("searcher") String searcher,
                                      @QueryParam("q") String query,
                                      @QueryParam("country") @DefaultValue("de") String country) {
        if (unauthorized(user)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return getSearcherResponse(searcher, country);

    }

    private boolean unauthorized(@Auth User user) {
        return user == null;
    }

    private Response getSearcherResponse(String searcher, String country) {
        if (State.erraticMode()) {
            Response.Status[] status = Response.Status.values();
            return Response.status(status[random.nextInt(status.length)]).build();
        }

        try {
            waitRandomTime();
            return Response.ok(getContent(searcher, country)).type(getFormat(searcher)).build();
        } catch (IOException | IllegalArgumentException e) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    private void waitRandomTime() {
        if (State.maxWait() > 0) {
            Uninterruptibles.sleepUninterruptibly(random.nextInt(State.maxWait()), TimeUnit.MILLISECONDS);
        }
    }

    @Path("/zoom")
    @GET
    public Response respondAsSearcher(@Auth User user, @QueryParam("q") String query, @QueryParam("size") int size) {
        if (unauthorized(user)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return getSearcherResponse("zoom", "br");
    }

    private String getContent(String searcher, String country) throws IOException {
        URL resource = getResource(searcher + "/" + searcher + "_" + country);
        return Resources.toString(resource, Charsets.UTF_8);
    }

    private String getFormat(String searcher) throws IOException {
        return Resources.toString(getResource(searcher + "/format"), Charsets.UTF_8);
    }

}
