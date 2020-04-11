package com.applications.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Service
@Path("/slv.co")
public class URLShortenerResource {

    @GET
    @Timed
    @ExceptionMetered
    @Path("/{id}")
    public String getUrl (@PathParam("id") String id) {
        return id;
    }

    @POST
    @Timed
    @ExceptionMetered
    @Path("/")
    public String create(String url) {

        throw new RuntimeException("URL is invalid.");
    }
}
