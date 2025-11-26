package com.anton.url_shortener.logic;

import org.jetbrains.annotations.NotNull;

public class Base62Encoder {
    private static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALLOWED_CHARS.length();

    public String encode(long id) {

        if (id == 0) {
            return String.valueOf(ALLOWED_CHARS.charAt(0));
        }

        var builder = new StringBuilder();
        while (id > 0) {
            int remainder = Math.toIntExact(id % BASE);
            builder.append(ALLOWED_CHARS.charAt(remainder));
            id /= BASE;
        }
        return builder.reverse().toString();
    }


    public Long decode(@NotNull String shortUrl) {

        int powerOf = 0;
        long decoded = 0;

        for (int i = shortUrl.length() - 1; i >= 0 ; i--) {
            int value = ALLOWED_CHARS.indexOf(shortUrl.charAt(i));
            decoded += (long) (value * Math.pow(BASE, powerOf));
            powerOf++;
        }

        return decoded;

    }

}
