package com.galibhaskar.URL_Shortner.concerns;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Date;

public interface IHelperService {
    String generateRandomString(int length);

    Date parseDateString(String dateString) throws Exception;

    String formatDateObject(Date date) throws Exception;

    Date getExtendedDate(Date expiryDate, int daysToAddToExpiryDate) throws Exception;

    String extractRequestBody(HttpServletRequest request) throws IOException;

    <T> T deserializeJSONString(String json, Class<T> className) throws Exception;
}
