package com.galibhaskar.URL_Shortner.controllers;

import com.galibhaskar.URL_Shortner.models.ItemBody;
import com.galibhaskar.URL_Shortner.models.PremiumBody;
import com.galibhaskar.URL_Shortner.models.ShortURL;
import com.galibhaskar.URL_Shortner.services.URLService;
import com.galibhaskar.URL_Shortner.utils.HelperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
public class AppController {
    private final URLService URLService;
    private final HelperService helperService;

    public AppController(@Autowired URLService URLService,
                         @Autowired HelperService helperService) {
        this.URLService = URLService;
        this.helperService = helperService;
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
                                                  @RequestAttribute String requestBody)
            throws Exception {

        String currentHostName = request.getScheme() + "://" +
                request.getHeader("Host") + "/";

        LinkedHashMap<String, String> map = helperService.deserializeJSONString(requestBody, LinkedHashMap.class);

        String longURL = map.get("longURL");

        if (longURL.isEmpty())
            throw new Exception("longURL empty");

        String shortCode = URLService.createShortURL(longURL, null);

        return ResponseEntity.status(HttpStatus.OK).body(currentHostName + shortCode);
    }

    @RequestMapping(path = "/premium/create", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createPremiumShortCode(HttpServletRequest request,
                                                         @RequestAttribute String requestBody)
            throws Exception {

        String currentHostName = request.getScheme() + "://" + request.getHeader("Host") + "/";

        PremiumBody deserializedBody = helperService.deserializeJSONString(requestBody, PremiumBody.class);

        if (deserializedBody.getDestinationUrl().isEmpty() ||
                deserializedBody.getSlashTag().isEmpty())
            throw new Exception("One or more required fields are empty");

        String shortCode = URLService.createShortURL(
                deserializedBody.getDestinationUrl(),
                deserializedBody.getSlashTag());

        return ResponseEntity.status(HttpStatus.OK).body(currentHostName + shortCode);
    }

    @RequestMapping(path = "/updateURL", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateLongURLofShortCode(@RequestAttribute String requestBody)
            throws Exception {
        ShortURL deserializedBody = helperService.deserializeJSONString(requestBody, ShortURL.class);

        if (deserializedBody.getShortCode().isEmpty() ||
                deserializedBody.getLongURL().isEmpty())
            throw new Exception("One or more required fields are empty");

        boolean updateStatus = URLService.updateShortURL(deserializedBody.getShortCode(),
                deserializedBody.getLongURL());

        if (updateStatus)
            return ResponseEntity.status(HttpStatus.OK).body("URL Update Success");

        else
            throw new Exception("URL Update Failed");
    }

    @RequestMapping(path = "/updateExpiry", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateExpiryOfShortCode(@RequestAttribute String requestBody)
            throws Exception {

        ItemBody body = helperService.deserializeJSONString(requestBody, ItemBody.class);

        if (body.getShortURL().isEmpty() ||
                body.getDaysToAdd() == 0)
            throw new Exception("One or more required fields are empty");

        boolean updateStatus = URLService.updateExpiry(body.getShortURL(), body.getDaysToAdd());

        if (updateStatus)
            return ResponseEntity.status(HttpStatus.OK).body("Expiry Date Updated Successfully");

        else
            throw new Exception("Expiry Update Failed");
    }

    @RequestMapping(path = "/deleteShortURL", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteShortURL(@RequestAttribute String requestBody)
            throws Exception {

        LinkedHashMap<String, String> map = helperService.deserializeJSONString(requestBody, LinkedHashMap.class);

        String shortURL = map.get("shortURL");

        if (shortURL.isEmpty())
            throw new Exception("Short URL is empty");

        boolean updateStatus = URLService.deleteByShortURL(shortURL);

        if (updateStatus)
            return ResponseEntity.status(HttpStatus.OK).body("Expiry Date Updated Successfully");

        else
            throw new Exception("Expiry Update Failed");
    }

}
