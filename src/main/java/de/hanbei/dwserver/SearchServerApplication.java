package de.hanbei.dwserver;

import de.hanbei.dwserver.auth.BasicAuthAutenticator;
import de.hanbei.dwserver.auth.User;
import de.hanbei.dwserver.resources.SearchResource;
import de.hanbei.dwserver.resources.StateResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

public class SearchServerApplication extends Application<SearchServerConfiguration> {

    @Override
    public void run(SearchServerConfiguration configuration, Environment environment) throws Exception {
        BasicAuthAutenticator authenticator = new BasicAuthAutenticator();

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(authenticator)
                        .setAuthorizer(authenticator)
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        environment.jersey().register(SearchResource.class);
        environment.jersey().register(StateResource.class);
    }

    @Override
    public void initialize(Bootstrap<SearchServerConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public String getName() {
        return "searchy";
    }

    public static void main(String[] args) throws Exception {
        new SearchServerApplication().run(args);
    }
}
