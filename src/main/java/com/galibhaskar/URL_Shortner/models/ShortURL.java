package com.galibhaskar.URL_Shortner.models;

import com.opencsv.bean.CsvBindByName;

import java.util.Date;

public class ShortURL {
    @CsvBindByName(column = "longURL")
    private String longURL;

    @CsvBindByName(column = "shortCode")
    private String shortCode;

    @CsvBindByName(column = "expiryDate")
    private String expiryDate;

    public ShortURL() {
    }

    public ShortURL(String longURL, String shortCode, String expiryDate) {
        this.longURL = longURL;
        this.shortCode = shortCode;
        this.expiryDate = expiryDate;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
