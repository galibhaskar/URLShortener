package com.galibhaskar.URL_Shortner.controllers;

import com.galibhaskar.URL_Shortner.models.ItemBody;
import com.galibhaskar.URL_Shortner.models.PremiumBody;
import com.galibhaskar.URL_Shortner.models.ShortURL;
import com.galibhaskar.URL_Shortner.services.URLService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AppController {
    private final URLService URLService;

    public AppController(@Autowired URLService URLService) {
        this.URLService = URLService;
    }

    @RequestMapping(path = "/{shortURL}", method = RequestMethod.GET)
    public void redirectToLongURL(HttpServletResponse response, @PathVariable String shortURL)
            throws Exception {
        String longURL = URLService.getLongURL(shortURL);

        response.sendRedirect(longURL);
    }

    @RequestMapping(path = "/info/{shortURL}", method = RequestMethod.GET)
    public ResponseEntity<ShortURL> getShortURLInfo(@PathVariable String shortURL)
            throws Exception {
        return new ResponseEntity<>(URLService.getURLInfo(shortURL), HttpStatus.OK);
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> createShortCode(HttpServletRequest request,
                                                  @RequestBody ShortURL body)
            throws Exception {

        String currentHostName = request.getScheme() + "://" +
                request.getHeader("Host") + "/";

        String shortCode = URLService.createShortURL(
                body.getLongURL(),
                body.getExpiryDate(),
                null);

        return ResponseEntity.status(HttpStatus.OK).body(currentHostName + shortCode);
    }

    @RequestMapping(path = "/premium/create", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createPremiumShortCode(HttpServletRequest request,
                                                         HttpServletResponse response,
                                                         @RequestBody PremiumBody premiumBody)
            throws Exception {

        String currentHostName = request.getScheme() + "://" + request.getHeader("Host") + "/";

        String shortCode = URLService.createShortURL(
                premiumBody.getDestinationUrl(),
                premiumBody.getExpiryDate(),
                premiumBody.getSlashTag());

        return ResponseEntity.status(HttpStatus.OK).body(currentHostName + shortCode);
    }

    @RequestMapping(path = "/updateURL", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateLongURLofShortCode(HttpServletResponse response,
                                                           @RequestBody ShortURL body) throws Exception {

        boolean updateStatus = URLService.updateShortURL(body.getShortCode(), body.getLongURL());

        if (updateStatus)
            return ResponseEntity.status(HttpStatus.OK).body("URL Update Success");

        else
            throw new Exception("URL Update Failed");
    }

    @RequestMapping(path = "/updateExpiry", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateExpiryOfShortCode(HttpServletResponse response,
                                                          @RequestBody ItemBody body) throws Exception {

        boolean updateStatus = URLService.updateExpiry(body.shortURL, body.daysToAdd);

        if (updateStatus)
            return ResponseEntity.status(HttpStatus.OK).body("Expiry Date Updated Successfully");

        else
            throw new Exception("Expiry Update Failed");
    }

}
