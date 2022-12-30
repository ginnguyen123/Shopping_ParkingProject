package project.shopping_system.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.time.OffsetDateTime.parse;

public class DateTimeUtil {
    private static final String FORMAT_TIME = "hh:mm:ss dd-MM-yyyy";
    private static final String DAY_FORMAT_TIME = "dd-MM-yyyy";
    private static final String MONTH_FORMAT_TIME = "MM-yyyy";
    private static final String YEAR_FORMAT_TIME = "yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_TIME);
    private static SimpleDateFormat simpleDateFormatAsDay = new SimpleDateFormat(DAY_FORMAT_TIME);
    private static SimpleDateFormat simpleDateFormatAsMonth = new SimpleDateFormat(MONTH_FORMAT_TIME);
    private static SimpleDateFormat simpleDateFormatAsYear = new SimpleDateFormat(YEAR_FORMAT_TIME);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_TIME).withZone(ZoneId.systemDefault());
    private static DateTimeFormatter dayTimeFormatter = DateTimeFormatter.ofPattern(DAY_FORMAT_TIME).withZone(ZoneId.systemDefault());
    private static DateTimeFormatter monthTimeFormatter = DateTimeFormatter.ofPattern(MONTH_FORMAT_TIME).withZone(ZoneId.systemDefault());
    private static DateTimeFormatter yearTimeFormatter = DateTimeFormatter.ofPattern(YEAR_FORMAT_TIME).withZone(ZoneId.systemDefault());
//    public static String formatDate(Date date){
//        return simpleDateFormat.format(date);
//    }
    public static String formatIntanceToString(Instant instantDate){
        System.out.println(instantDate);
        return dateTimeFormatter.format(instantDate);
    }
    public static String formatDayIntanceToString(Instant instantDate){
        return dayTimeFormatter.format(instantDate);
    }
    public static String formatMonthIntanceToString(Instant instantDate){
        return monthTimeFormatter.format(instantDate);
    }
    public static String formatYearIntanceToString(Instant instantDate){
        return yearTimeFormatter.format(instantDate);
    }
    public static Instant formatStringToInstanAsDay(String str){
        Instant instant = null;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DAY_FORMAT_TIME);
        try{
            Date date = simpleDateFormatAsDay.parse(str);
            instant = date.toInstant();
        }catch (ParseException parseException){
            parseException.printStackTrace();
        }
        return instant;
    }
    public static Instant formatStringToInstanAsMonth(String str){
        Instant instant = null;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MONTH_FORMAT_TIME);
        try{
            Date date = simpleDateFormatAsMonth.parse(str);
            instant = date.toInstant();
        }catch (ParseException parseException){
            parseException.printStackTrace();
        }
        return instant;
    }
    public static Instant formatStringToInstanAsYear(String str){
        Instant instant = null;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YEAR_FORMAT_TIME);
        try{
            Date date = simpleDateFormatAsYear.parse(str);
            instant = date.toInstant();
        }catch (ParseException parseException){
            parseException.printStackTrace();
        }
        return instant;
    }

}
