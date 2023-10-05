package com.galibhaskar.URL_Shortner.concerns;

import com.galibhaskar.URL_Shortner.models.ShortURL;

import java.util.List;

public interface ICSVService {
    void addItemToCSV(ShortURL newItem) throws Exception;

    void writeToCSV(List<ShortURL> newData);

    List<ShortURL> readFromCSV() throws Exception;
}
