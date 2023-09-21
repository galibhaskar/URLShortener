package com.galibhaskar.URL_Shortner.controllers;

import com.galibhaskar.URL_Shortner.models.ItemBody;
import com.galibhaskar.URL_Shortner.models.ShortURL;
import com.galibhaskar.URL_Shortner.services.DatabaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
public class AppController {

    @Autowired
    private DatabaseService databaseService;

    @RequestMapping(path = "/{shortURL}", method = RequestMethod.GET)
    public void redirectToLongURL(HttpServletResponse response, @PathVariable String shortURL) {
        try {
            String longURL = databaseService.getLongURL(shortURL);

            response.sendRedirect(longURL);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Url not found", e);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not redirect to the full url", e);
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json")
    public String createShortCode(HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody ShortURL body) throws Exception {
        String currentHostName = request.getScheme() + "://" + request.getHeader("Host") + "/";

        String shortCode = databaseService.createShortURL(body.getLongURL(), body.getExpiryDate());

        if (shortCode.isEmpty()) {
            throw new Exception("Short code empty");
        } else {
            response.setStatus(200);
            return currentHostName + shortCode;
        }
    }

    @RequestMapping(path = "/updateURL", method = RequestMethod.PATCH)
    public void updateLongURLofShortCode(HttpServletResponse response,
                                         @RequestBody ShortURL body) throws IOException {

        boolean updateStatus = databaseService.updateShortURL(body.getShortCode(), body.getLongURL());

        if (updateStatus)
            response.setStatus(200);

        throw new IOException("Something went wrong");
    }

    @RequestMapping(path = "/updateExpiry", method = RequestMethod.PATCH)
    public void updateExpiryofShortCode(HttpServletResponse response,
                                        @RequestBody ItemBody body) throws IOException {

        boolean updateStatus = databaseService.updateExpiry(body.shortURL, body.daysToAdd);

        if (updateStatus)
            response.setStatus(200);

        throw new IOException("Something went wrong");
    }

}
