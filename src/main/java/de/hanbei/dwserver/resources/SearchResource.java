package de.hanbei.dwserver.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Uninterruptibles;
import de.hanbei.dwserver.model.Offer;
import de.hanbei.dwserver.model.StaticResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkState;

@Path("/search")
public class SearchResource {

    private Random random;

    public SearchResource() {
        this.random = new Random();
    }

    @Path("/{searcher}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response respond(@PathParam("searcher") String keyword, @QueryParam("size") int size) {
        checkState(StaticResponse.offers != null);

        ArrayList<Offer> resultList = Lists.newArrayList(StaticResponse.offers);
        Collections.shuffle(resultList);

        if (size <= 0) {
            size = resultList.size();
        }
        Uninterruptibles.sleepUninterruptibly(random.nextInt(2000), TimeUnit.MILLISECONDS);
        return Response.ok(resultList.subList(0, Math.min(size, resultList.size()))).build();
    }
}