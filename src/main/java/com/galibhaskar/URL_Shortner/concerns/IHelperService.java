package com.galibhaskar.URL_Shortner.concerns;

import com.galibhaskar.URL_Shortner.models.ShortURL;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;

public interface IHelperService {
    String generateRandomString(int length);

    Date parseDateString(String dateString) throws Exception;

    String formatDateObject(Date date) throws Exception;

    String getExtendedDate(String expiryDate, int daysToAddToExpiryDate) throws Exception;

    String extractRequestBody(ContentCachingRequestWrapper requestWrapper) throws IOException;

    <T> T deserializeJSONString(String json, Class<T> className) throws Exception;
}
