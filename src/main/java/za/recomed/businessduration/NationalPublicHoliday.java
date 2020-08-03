package za.recomed.businessduration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

public class NationalPublicHoliday {
    static private Logger logger = LoggerFactory.getLogger(NationalPublicHoliday.class);
    private static final Map<String,String> publicHolidays;

    static {
        publicHolidays = new HashMap<>();
        publicHolidays.put("1-1", "NEW_YEARS_DAY");
        publicHolidays.put("3-21", "HUMAN_RIGHT'S_DAY");
        publicHolidays.put("4-10", "GOOD_FRIDAY");
        publicHolidays.put("4-13", "FAMILY_DAY");
        publicHolidays.put("4-27", "FREEDOM_DAY");
        publicHolidays.put("5-1", "WORKERS_DAY");
        publicHolidays.put("5-25", "AFRICA_DAY");
        publicHolidays.put("6-16", "YOUTH_DAY");
        publicHolidays.put("7-18", "NELSON_MANDELAS_BIRTHDAY");
        publicHolidays.put("8-9", "WOMENS_DAY");
        publicHolidays.put("9-24", "HERITAGE_DAY");
        publicHolidays.put("12-16", "RECONCILIATION_DAY");
        publicHolidays.put("12-25", "CHRISTMAS_DAY");
        publicHolidays.put("12-26", "DAY_OF_GOODWILL");
    }

    public static Boolean isPublicHoliday(LocalDate enquiry){
        String holidayKey = new StringBuilder().append(enquiry.getMonthValue()).append("-").append(enquiry.getDayOfMonth()).toString();
        String holiday = publicHolidays.get(holidayKey);
        logger.debug(new StringBuilder("Holiday enquired=").append(holiday).append(" | ").append(holidayKey).toString());
        return holiday != null && !holiday.trim().isEmpty();
    }

}
