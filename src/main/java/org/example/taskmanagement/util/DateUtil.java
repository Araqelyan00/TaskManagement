package org.example.taskmanagement.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");

    public Date parseToDate(String dateStr) throws ParseException {
        return SDF.parse(dateStr);
    }

    public String dateToString(Date date){
        return SDF.format(date);
    }
}