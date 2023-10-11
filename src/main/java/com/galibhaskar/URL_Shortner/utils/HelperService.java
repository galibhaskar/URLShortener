package com.galibhaskar.URL_Shortner.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galibhaskar.URL_Shortner.concerns.IHelperService;
import com.galibhaskar.URL_Shortner.models.ShortURL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class HelperService implements IHelperService {
    private static final String PATTERN = "MM-dd-yyyy";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);

    @Override
    public String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());

            char randomChar = CHARACTERS.charAt(randomIndex);

            sb.append(randomChar);
        }

        return sb.toString();
    }

    @Override
    public Date parseDateString(String dateString) throws Exception {
        return dateFormat.parse(dateString);
    }

    @Override
    public String formatDateObject(Date date) throws Exception {
        return dateFormat.format(date);
    }

    public String getExtendedDate(String expiryDate, int daysToAddToExpiryDate)
            throws Exception {
        Date convertedExpiryDate = parseDateString(expiryDate);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(convertedExpiryDate);

        calendar.add(Calendar.DAY_OF_MONTH, daysToAddToExpiryDate);

        Date newDate = calendar.getTime();

        return formatDateObject(newDate);
    }

    @Override
    public String extractRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader = null;
        StringBuilder requestBody = new StringBuilder();

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    requestBody.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
//            throw ex;
            System.out.println(ex);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // Log or handle the exception as needed
                    System.out.println("Error closing BufferedReader: " + e);
//                    throw e;
                }
            }
        }
        return requestBody.toString();
    }

    @Override
    public <T> T deserializeJSONString(String json, Class<T> className) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return objectMapper.readValue(json, className);
    }
}
