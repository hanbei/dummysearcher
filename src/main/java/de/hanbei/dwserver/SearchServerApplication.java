package de.hanbei.dwserver;

import de.hanbei.dwserver.auth.BasicAuthAutenticator;
import de.hanbei.dwserver.auth.User;
import de.hanbei.dwserver.resources.SearchResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class SearchServerApplication extends Application<SearchServerConfiguration> {

    @Override
    public void run(SearchServerConfiguration configuration, Environment environment) throws Exception {
        BasicAuthAutenticator authenticator = new BasicAuthAutenticator();

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(authenticator)
                        .setAuthorizer(authenticator)
                        //.setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        environment.jersey().register(SearchResource.class);
    }

    @Override
    public void initialize(Bootstrap<SearchServerConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public String getName() {
        return "searchy";
    }

    public static void main(String[] args) throws Exception {
        new SearchServerApplication().run(args);
    }
}
