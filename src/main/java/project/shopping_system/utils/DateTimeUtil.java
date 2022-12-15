package project.shopping_system.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {
    private static final String FORMAT_TIME = "hh:mm:ss dd-MM-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_TIME);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_TIME).withZone(ZoneId.systemDefault());

    public static String formatDate(Date date){
        return simpleDateFormat.format(date);
    }
    public static String formatIntanceToString(Instant instantDate){
        return dateTimeFormatter.format(instantDate);
    }
    public Date formatStringToDate(String stringDate){
        Date simpleDateFormat1 = null;
        try{
            simpleDateFormat1 = simpleDateFormat.parse(stringDate);
        }catch (ParseException parseException){
            parseException.printStackTrace();
        }
        return simpleDateFormat1;
    }
}
