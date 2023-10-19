package com.galibhaskar.URL_Shortner.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "short_urls_mapper")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer Id;

    @Column(name = "long_url")
    private String longURL;

    @Column(name = "short_code")
    private String shortCode;

    @Column(name = "expiry_date")
    private Date expiryDate;

    public ShortURL(String longURL, String shortCode, Date expiryDate) {
        this.longURL = longURL;
        this.shortCode = shortCode;
        this.expiryDate = expiryDate;
    }
}
