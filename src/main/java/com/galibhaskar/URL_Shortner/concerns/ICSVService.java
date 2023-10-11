package com.galibhaskar.URL_Shortner.concerns;

import com.galibhaskar.URL_Shortner.models.ShortURL;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.util.List;

public interface ICSVService {
    void addItemToCSV(ShortURL newItem) throws Exception;

    void writeToCSV(List<ShortURL> newData) throws Exception;

    List<ShortURL> readFromCSV() throws Exception;
}
