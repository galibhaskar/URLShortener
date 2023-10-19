package com.galibhaskar.URL_Shortner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PremiumBody {
    private String destinationUrl;

    private String slashTag;

    private String userType;
}
