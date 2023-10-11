package com.galibhaskar.URL_Shortner.schedulers;

import com.galibhaskar.URL_Shortner.concerns.IURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class URLScheduler {
    private final IURLService databaseService;

    public URLScheduler(@Autowired IURLService databaseService) {
        this.databaseService = databaseService;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    void clearExpiredURLs() throws Exception {
        System.out.println("clearing expired URLs....");
        databaseService.deleteExpiredURLs();
        System.out.println("cleared all expired URLs");
    }
}
