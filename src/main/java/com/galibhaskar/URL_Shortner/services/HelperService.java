package com.galibhaskar.URL_Shortner.services;

import com.galibhaskar.URL_Shortner.concerns.IHelperService;
import org.springframework.stereotype.Component;

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
    public Date parseDateString(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }

    @Override
    public String formatDateObject(Date date) {
        return dateFormat.format(date);
    }

    public String getExtendedDate(String expiryDate, int daysToAddToExpiryDate)
            throws ParseException {
        Date convertedExpiryDate = parseDateString(expiryDate);

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(convertedExpiryDate);

        calendar.add(Calendar.DAY_OF_MONTH, daysToAddToExpiryDate);

        Date newDate = calendar.getTime();

        return formatDateObject(newDate);
    }
}
