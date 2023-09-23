package com.galibhaskar.URL_Shortner.concerns;

import com.galibhaskar.URL_Shortner.models.ShortURL;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IDatabaseService {
    ShortURL getURLInfo(String shortURL) throws Exception;

    String getLongURL(String shortURL) throws Exception;

    String createShortURL(String longURL, String expiry) throws Exception;

    boolean updateShortURL(String shortURL, String longURL);

    boolean updateExpiry(String shortURL, int daysToAddToExpiryDate) throws ParseException;
}
