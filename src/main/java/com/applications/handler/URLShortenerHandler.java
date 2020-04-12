package com.applications.handler;

import com.applications.exception.InvalidURLException;
import com.applications.exception.ShortURLAlreadyExistException;
import com.applications.exception.ShortURLNotFoundException;
import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class URLShortenerHandler {

    private static final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
    private static final String SHORT_URL_PREFIX = "localhost:8080/ita.ly/";
    private static final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    public void redirectToOriginalURL(String id, HttpServletResponse response) throws ShortURLNotFoundException {
        try (Jedis jedis = pool.getResource()) {
            String url = jedis.get(id);
            if (url == null)
                throw new ShortURLNotFoundException("No ita.ly URL Exists for given ID: " + id);

            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String createTinyUrl(String url) {
        if (!urlValidator.isValid(url))
            throw new InvalidURLException("Invalid URL: " + url);

        String id = getHashValue(url);
        try (Jedis jedis = pool.getResource()) {
            if (jedis.get(id) != null)
                throw new ShortURLAlreadyExistException("Short URL already exists.");
            jedis.set(id, url);
        }
        return SHORT_URL_PREFIX + id;
    }

    private String getHashValue(String url) {
        return Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
    }
}
