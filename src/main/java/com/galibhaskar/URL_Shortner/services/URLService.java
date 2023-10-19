package com.galibhaskar.URL_Shortner.services;

import com.galibhaskar.URL_Shortner.concerns.IHelperService;
import com.galibhaskar.URL_Shortner.models.ShortURL;
import com.galibhaskar.URL_Shortner.concerns.IURLService;
import com.galibhaskar.URL_Shortner.repositories.IURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class URLService implements IURLService {
    final IURLRepository urlRepository;

    final IHelperService helperService;

    @Value("${shortCodeLength:5}")
    private int SHORT_CODE_LENGTH;

    @Value("${defaultExpiryDate:30}")
    private int DEFAULT_EXPIRY_DATE;

    public URLService(@Autowired IURLRepository urlRepository,
                      @Autowired IHelperService helperService) {
        this.urlRepository = urlRepository;

        this.helperService = helperService;
    }

    @Override
    public ShortURL getURLInfo(String shortURL) throws Exception {
        return urlRepository.findByShortCode(shortURL);
    }

    @Override
    public String getLongURL(String shortURL) throws Exception {

        ShortURL validShortURL = urlRepository.findByShortCodeAndExpiryDateGreaterThan(shortURL, new Date());

        if (validShortURL == null)
            throw new RuntimeException("URL EXPIRED/URL NOT FOUND");
//        if (result.isEmpty())
//            throw new RuntimeException("URL Not Found");
//
//        String expiryDate = result.get().getExpiryDate();

//        Date convertedExpiryDate = helperService.parseDateString(expiryDate);

//        if (currentDate.after(convertedExpiryDate))
//            throw new RuntimeException("URL Expired");

//            System.out.println(result);

        return validShortURL.getLongURL();
    }

    @Override
    public String createShortURL(String longURL, String customShortCode)
            throws Exception {

        String shortCode;
        if (customShortCode == null)
            shortCode = helperService.generateRandomString(SHORT_CODE_LENGTH);
        else
            shortCode = customShortCode;

        Date todaysDate = new Date();

        Date expiryDate = helperService.getExtendedDate(todaysDate, DEFAULT_EXPIRY_DATE);

        ShortURL newShortURL = new ShortURL(longURL, shortCode, expiryDate);

        urlRepository.save(newShortURL);

        System.out.println("Generated the ID:" + newShortURL.getId());

        return shortCode;
    }

    @Override
    public boolean updateShortURL(String shortURL, String longURL) {
        Integer modifiedRows = urlRepository.updateLongURLByShortURL(shortURL, longURL);

        System.out.println("modifiedRows:" + modifiedRows);

        if (modifiedRows == 0)
            throw new RuntimeException("Short URL Update Failed");

        return true;
    }

    @Override
    public boolean updateExpiry(String shortURL, int daysToAddToExpiryDate) throws Exception {
        ShortURL validShortURL = urlRepository.findByShortCode(shortURL);

        Date newExpiryDate = helperService.getExtendedDate(validShortURL.getExpiryDate(), daysToAddToExpiryDate);

        Integer modifiedRows = urlRepository.updateExpiryDateByShortURL(shortURL, newExpiryDate);

        if (modifiedRows == 0)
            throw new RuntimeException("Expiry Date Update Failed");

        return true;
    }

    @Override
    public void deleteExpiredURLs() {
        urlRepository.deleteByExpiryDateLessThan(new Date());
    }

    @Override
    public boolean deleteByShortURL(String shortURL) {
        Integer modifiedRows = urlRepository.deleteByShortURL(shortURL);

        if (modifiedRows == 0)
            throw new RuntimeException("Delete Failed");

        return true;
    }
}
