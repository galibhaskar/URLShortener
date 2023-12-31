package com.galibhaskar.URL_Shortner.concerns;

import com.galibhaskar.URL_Shortner.models.ShortURL;

import java.util.Date;

public interface IURLService {
    ShortURL getURLInfo(String shortURL) throws Exception;

    String getLongURL(String shortURL) throws Exception;

    String createShortURL(String longURL, String customShortCode) throws Exception;

    boolean updateShortURL(String shortURL, String longURL) throws Exception;

    boolean updateExpiry(String shortURL, int daysToAddToExpiryDate) throws Exception;

    void deleteExpiredURLs() throws Exception;

    boolean deleteByShortURL(String shortURL);
}
