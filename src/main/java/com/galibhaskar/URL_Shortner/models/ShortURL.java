package com.galibhaskar.URL_Shortner.models;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortURL {
    @CsvBindByName(column = "longURL")
    private String longURL;

    @CsvBindByName(column = "shortCode")
    private String shortCode;

    @CsvBindByName(column = "expiryDate")
    private String expiryDate;
}
