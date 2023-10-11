package com.galibhaskar.URL_Shortner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemBody {
    private String shortURL;

    private int daysToAdd;
}
