package com.galibhaskar.URL_Shortner.repositories;

import com.galibhaskar.URL_Shortner.models.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Transactional
public interface IURLRepository extends JpaRepository<ShortURL, Integer> {

    ShortURL findByShortCodeAndLongURL(String shortURL, String longURL);

    ShortURL findByShortCode(String shortURL);

    ShortURL findByShortCodeAndExpiryDateGreaterThan(String shortURL, Date date);

    @Modifying
    @Query("update ShortURL u set u.longURL = :longURL where u.shortCode = :shortURL")
    Integer updateLongURLByShortURL(@Param("shortURL") String shortURL, @Param("longURL") String longURL);

    @Modifying
    @Query("update ShortURL u set u.expiryDate = :expiryDate where u.shortCode = :shortURL")
    Integer updateExpiryDateByShortURL(@Param("shortURL") String shortURL, @Param("expiryDate") Date newExpiryDate);

//    @Query("delete ShortURL u where u.expiryDate < :expiryDate")
    void deleteByExpiryDateLessThan(Date date);

    @Modifying
    @Query("delete ShortURL u where u.shortCode = :shortURL")
    Integer deleteByShortURL(@Param("shortURL") String shortURL);
}
