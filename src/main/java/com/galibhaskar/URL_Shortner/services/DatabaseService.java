package com.galibhaskar.URL_Shortner.services;

import com.galibhaskar.URL_Shortner.models.ShortURL;
import com.galibhaskar.URL_Shortner.concerns.IDatabaseService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class DatabaseService implements IDatabaseService, ApplicationRunner {
    List<ShortURL> data;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());

            char randomChar = CHARACTERS.charAt(randomIndex);

            sb.append(randomChar);
        }

        return sb.toString();
    }


    @Value("${csvFilePath:DefaultCSV.csv}")
    private String databaseFilePath;

    @Override
    public List<ShortURL> getAllShortURLs() {
        return null;
    }

    @Override
    public String getLongURL(String shortURL) {
        Optional<ShortURL> result = this.data.stream()
                .filter(_url -> Objects.equals(_url.getShortCode(), shortURL))
                .findFirst();

        if (result.isEmpty())
            throw new NoSuchElementException();

//        else if(result.isPresent() && result.get().getExpiryDate())
//            throw new IOException();

        return result.get().getLongURL();
    }

    @Override
    public String createShortURL(String longURL, String expiry) throws Exception {
        String shortCode = generateRandomString(5);

        addItemToCSV(new ShortURL(longURL, shortCode, expiry));

        return shortCode;
    }

    @Override
    public boolean updateShortURL(String shortURL, String longURL) {
        AtomicBoolean updateSuccess = new AtomicBoolean(false);

        this.data.forEach(_urlItem -> {
            if (Objects.equals(_urlItem.getShortCode(), shortURL)) {
                _urlItem.setLongURL(longURL);

                updateSuccess.set(true);
            }
        });

        return updateSuccess.get();
    }

    @Override
    public boolean updateExpiry(String shortURL, int daysToAddToExpiryDate) {
        return false;
    }

    public void addItemToCSV(ShortURL newItem) throws Exception {
        // Read the existing data from the CSV file
        List<ShortURL> existingData = readFromCSV();

        // Add the new item to the existing data
        existingData.add(newItem);

        this.data = existingData;

        // Write the updated data back to the CSV file
        writeToCSV(existingData);
    }

    private void writeToCSV(List<ShortURL> newData) {
        try {
            Resource resource = new ClassPathResource(databaseFilePath);

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

    private List<ShortURL> readFromCSV() throws Exception {
        List<ShortURL> data = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource(databaseFilePath);

            File databaseFile = resource.getFile();

            FileReader reader = new FileReader(databaseFile);

            CsvToBean<ShortURL> csvToBean = new CsvToBeanBuilder<ShortURL>(reader)
                    .withType(ShortURL.class).build();

            data = csvToBean.parse();

            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        return data;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.data = readFromCSV();

        this.data.forEach(obj -> System.out.println(obj.getLongURL()));

//        this.createShortURL("test.com", "09-20-2023");
    }
}