package de.hanbei.dwserver.resources;

import com.google.common.base.Charsets;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.google.common.io.Resources.getResource;

@Path("/search")
public class SearchResource {

    private final Random random;

    private final LoadingCache<Tuple, Tuple> cache = CacheBuilder.newBuilder().maximumSize(1000).build(new CacheLoader<Tuple, Tuple>() {
        @Override
        public Tuple load(Tuple key) throws Exception {
            String format = getFormat(key.searcher());
            String content = getContent(key.searcher(), key.country());
            return new Tuple(format, content);
        }
    });

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
            Tuple tuple = cache.get(new Tuple(searcher, country));
            return Response.ok(tuple.country()).type(tuple.format()).build();
        } catch (ExecutionException | IllegalArgumentException e) {
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

    @Path("/random")
    @GET
    public Response randomData(@Auth User user, @QueryParam("q") String query, @QueryParam("size") int size) {
        if (State.erraticMode()) {
            Response.Status[] status = Response.Status.values();
            return Response.status(status[random.nextInt(status.length)]).build();
        }

        if (unauthorized(user)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        waitRandomTime();

        return Response.ok(randomData()).build();
    }

    private String randomData() {
        return "{\n" +
                "    \"offerSource\": \"kelkoo\",\n" +
                "    \"merchant\": \"14619313\",\n" +
                "    \"imageUrl\": \"http://r.kelkoo.com/r/br/14619313/125801/90/90/http%3A%2F%2Fwww.extra-imagens.com.br%2FControl%2FArquivoExibir.aspx%3FIdArquivo%3D159616688/WWBjsZTR3U7wsZoAP_rb_MAeozJ8KaEYspeMeIC1CqE-\",\n" +
                "    \"url\": \"http://ecs.br.kelkoo.com.br/ctl/go/offersearchGo?.ts=1455626808010&.sig=J2YeaZ1mbdPo7P9nv1x8rn2fRoA-&catId=125801&localCatId=125801&comId=14619313&offerId=cbf055c2224bfc2d82effc0f6de9064c&searchId=null&affiliationId=96947160&country=br&wait=true&contextLevel=2&service=11\",\n" +
                "    \"merchantImageUrl\": \"https://dihpdprqz3ylr.cloudfront.net/extra_com_br.jpg\",\n" +
                "    \"currency\": \"BRL\",\n" +
                "    \"id\": \"cbf055c2224bfc2d82effc0f6de9064c\",\n" +
                "    \"category\": \"Acessórios para Celulares\",\n" +
                "    \"title\": \"Apple iPhone 6 Apple com Tela 4 7” iOS 8 Touch ID Câmera iSight 8MP Wi Fi 3G 4G GPS MP3 Bluetooth e NFC Cinza Espacial\",\n" +
                "    \"price\": 3199.0,\n" +
                "    \"manufacturer\": \"Apple\",\n" +
                "    \"merchantName\": \"EXTRA\",\n" +
                "    \"estimatedCPC\": 0.0125,\n" +
                "    \"deliveryCost\": -1.0,\n" +
                "    \"availability\": \"GREEN\"\n" +
                "  }";
    }

    private String getContent(String searcher, String country) throws IOException {
        URL resource = getResource(searcher + "/" + searcher + "_" + country);
        return Resources.toString(resource, Charsets.UTF_8);
    }

    private String getFormat(String searcher) throws IOException {
        return Resources.toString(getResource(searcher + "/format"), Charsets.UTF_8);
    }

    private class Tuple {
        private final String first;
        private final String second;

        public Tuple(String first, String second) {
            this.first = first;
            this.second = second;
        }

        public String searcher() {
            return first;
        }

        public String country() {
            return second;
        }

        public String format() {
            return first;
        }

        public String content() {
            return second;
        }
    }
}
