package de.hanbei.dwserver;

import de.hanbei.dwserver.resources.SearchResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SearchServerApplication extends Application<SearchServerConfiguration> {

    @Override
    public void run(SearchServerConfiguration configuration, Environment environment) throws Exception {
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
