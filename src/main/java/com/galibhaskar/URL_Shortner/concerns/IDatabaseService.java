package com.galibhaskar.URL_Shortner.concerns;

import com.galibhaskar.URL_Shortner.models.ShortURL;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IDatabaseService {
    List<ShortURL> getAllShortURLs();

    String getLongURL(String shortURL);

    String createShortURL(String longURL, String expiry) throws Exception;

    boolean updateShortURL(String shortURL, String longURL);

    boolean updateExpiry(String shortURL, int daysToAddToExpiryDate);
}
