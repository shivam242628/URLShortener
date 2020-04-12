package com.applications.resources;

import com.applications.handler.URLShortenerHandler;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import org.jvnet.hk2.annotations.Service;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Service
@Path("/ita.ly")
public class URLShortenerResource {

    private final URLShortenerHandler urlShortenerHandler;

    public URLShortenerResource(URLShortenerHandler urlShortenerHandler) {
        this.urlShortenerHandler = urlShortenerHandler;
    }

    @GET
    @Timed
    @ExceptionMetered
    @Path("/{id}")
    public Response getUrl(@PathParam("id") String id,
                           @Context HttpServletResponse response) {
        return urlShortenerHandler.redirectToOriginalURL(id, response);
    }

    @POST
    @Timed
    @ExceptionMetered
    @Path("/")
    public Response create(String url) {
        return urlShortenerHandler.createTinyUrl(url);
    }
}
