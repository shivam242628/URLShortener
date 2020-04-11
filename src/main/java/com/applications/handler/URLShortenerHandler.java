package com.applications.handler;

import com.applications.exception.InvalidURL;
import com.applications.exception.URLNotFoundException;
import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class URLShortenerHandler {

    private static final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

    public Response redirectToOriginalURL(String id, HttpServletResponse response) throws URLNotFoundException {
        try (Jedis jedis = pool.getResource()) {
            String url = jedis.get(id);
            if (url == null) {
                throw new URLNotFoundException("No URL Exists for given ID: " + id);
            } else {
                response.sendRedirect(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok("SUCCESS", MediaType.APPLICATION_JSON).build();
    }

    public Response createTinyUrl(String url) {
        if (!isURLValid(url))
            throw new InvalidURL("Invalid URL");

        String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
        try (Jedis jedis = pool.getResource()) {
            jedis.set(id, url);
        }
        String shortenedUrl = "localhost:8080/ita.ly/" + id;
        return Response.ok(shortenedUrl, MediaType.APPLICATION_JSON).build();
    }

    public boolean isURLValid(String url) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"}
        );

        return urlValidator.isValid(url);
    }
}
