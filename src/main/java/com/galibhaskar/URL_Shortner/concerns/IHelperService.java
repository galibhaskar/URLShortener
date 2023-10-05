package com.galibhaskar.URL_Shortner.concerns;

import java.text.ParseException;
import java.util.Date;

public interface IHelperService {
    String generateRandomString(int length);

    Date parseDateString(String dateString) throws ParseException;

    String formatDateObject(Date date);

    String getExtendedDate(String expiryDate, int daysToAddToExpiryDate) throws ParseException;
}
