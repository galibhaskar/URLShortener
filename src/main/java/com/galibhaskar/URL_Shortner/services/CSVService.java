package com.galibhaskar.URL_Shortner.services;

import com.galibhaskar.URL_Shortner.concerns.ICSVService;
import com.galibhaskar.URL_Shortner.models.ShortURL;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVService implements ICSVService {
    @Value("${csvFilePath:DefaultCSV.csv}")
    private String csvFilePath;

    public void addItemToCSV(ShortURL newItem) {
        // Read the existing data from the CSV file
        List<ShortURL> existingData = readFromCSV();

        // Add the new item to the existing data
        existingData.add(newItem);

        // Write the updated data back to the CSV file
        writeToCSV(existingData);
    }

    public void writeToCSV(List<ShortURL> newData) {
        try {
            Resource resource = new ClassPathResource(csvFilePath);

            File databaseFile = resource.getFile();

            FileWriter writer = new FileWriter(databaseFile);

            StatefulBeanToCsv<ShortURL> csvToBean = new StatefulBeanToCsvBuilder<ShortURL>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withOrderedResults(false)
                    .build();

            csvToBean.write(newData);

            writer.close();

        } catch (Exception e) {
            System.out.println("write to CSV" + e);
        }
    }

    public List<ShortURL> readFromCSV() {
        List<ShortURL> data = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource(csvFilePath);

            File databaseFile = resource.getFile();

            FileReader reader = new FileReader(databaseFile);

            CsvToBean<ShortURL> csvToBean = new CsvToBeanBuilder<ShortURL>(reader)
                    .withType(ShortURL.class)
                    .build();

            data = csvToBean.parse();

            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return data;
    }

}
