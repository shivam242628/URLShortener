package com.applications;

import com.applications.handler.URLShortenerHandler;
import com.applications.resources.URLShortenerResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;


public class URLShortenerApplication extends Application<URLShortenerConfiguration> {

    public static void main(final String[] args) throws Exception {
        new URLShortenerApplication().run(args);
    }

    @Override
    public String getName() {
        return "URLShortener";
    }

    @Override
    public void initialize(final Bootstrap<URLShortenerConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final URLShortenerConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        URLShortenerHandler urlShortenerHandler = new URLShortenerHandler();
        URLShortenerResource urlShortenerResource = new URLShortenerResource(new URLShortenerHandler());

        environment.jersey().register(urlShortenerHandler);
        environment.jersey().register(urlShortenerResource);
    }

}
