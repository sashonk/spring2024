package ru.asocial.utils;

import java.time.LocalDate;

public class NullHelper {
    
    public static java.sql.Date nullSafeToDate(LocalDate ld) {
        return ld == null ? null : java.sql.Date.valueOf(ld);
    }
}
