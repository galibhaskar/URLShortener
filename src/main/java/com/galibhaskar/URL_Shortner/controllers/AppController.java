package com.galibhaskar.URL_Shortner.controllers;

import com.galibhaskar.URL_Shortner.models.ItemBody;
import com.galibhaskar.URL_Shortner.models.RequestItem;
import com.galibhaskar.URL_Shortner.models.ShortURL;
import com.galibhaskar.URL_Shortner.services.DatabaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.text.ParseException;
import java.util.NoSuchElementException;

@RestController
public class AppController {

    @Autowired
    private DatabaseService databaseService;

    @RequestMapping(path = "/{shortURL}", method = RequestMethod.GET)
    public String redirectToLongURL(HttpServletResponse response, @PathVariable String shortURL) {
        try {
            String longURL = databaseService.getLongURL(shortURL);

            response.sendRedirect(longURL);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Url not found", e);
        } catch (Exception e) {
            return e.toString();
        }

        return "";
    }

    @RequestMapping(path = "/info/{shortURL}", method = RequestMethod.GET)
    public ShortURL getShortURLInfo(HttpServletResponse response, @PathVariable String shortURL) {
        try {
            return databaseService.getURLInfo(shortURL);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Url not found", e);
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST, consumes = "application/json")
    public String createShortCode(HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody RequestItem requestItem) throws Exception {

        String currentHostName = request.getScheme() + "://" + request.getHeader("Host") + "/";

        String shortCode = databaseService.createShortURL(requestItem.longURL, requestItem.expiryDate);

        if (shortCode.isEmpty()) {
            throw new Exception("Short code empty");
        } else {
            response.setStatus(200);
            return currentHostName + shortCode;
        }
    }

    @RequestMapping(path = "/updateURL", method = RequestMethod.PATCH)
    public String updateLongURLofShortCode(HttpServletResponse response,
                                           @RequestBody ShortURL body) throws IOException {

        boolean updateStatus = databaseService.updateShortURL(body.getShortCode(), body.getLongURL());

        if (updateStatus) {
            response.setStatus(200);
            return "Update success";
        } else
            throw new IOException("Something went wrong");
    }

    @RequestMapping(path = "/updateExpiry", method = RequestMethod.PATCH)
    public String updateExpiryofShortCode(HttpServletResponse response,
                                          @RequestBody ItemBody body) throws IOException, ParseException {

        boolean updateStatus = databaseService.updateExpiry(body.shortURL, body.daysToAdd);

        if (updateStatus) {
            response.setStatus(200);
            return "updated expiry successfully";
        } else
            throw new IOException("Something went wrong");
    }

}
