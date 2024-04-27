package com.applydigital.hackernews.domain.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public final class InstantUtils {
    private InstantUtils() {}

    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

    public static String getMonth(Instant date){
        return DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH)
                .format(date.atZone(ZoneId.systemDefault()));
    }
}
