package za.recomed.businessduration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class NationalPublicHoliday {
    static private Logger logger = LoggerFactory.getLogger(NationalPublicHoliday.class);
    private static final Map<NationalPublicHoliday,String> publicHolidays;
    private int month;
    private int day;

    static {
        publicHolidays = new HashMap<>();
        publicHolidays.put(new NationalPublicHoliday(1,1), "NEW_YEARS_DAY");
        publicHolidays.put(new NationalPublicHoliday(3,21), "HUMAN_RIGHT'S_DAY");
        publicHolidays.put(new NationalPublicHoliday(4,19), "GOOD_FRIDAY");
        publicHolidays.put(new NationalPublicHoliday(4,21), "EASTER");
        publicHolidays.put(new NationalPublicHoliday(4,22), "FAMILY_DAY");
        publicHolidays.put(new NationalPublicHoliday(4,27), "FREEDOM_DAY");
        publicHolidays.put(new NationalPublicHoliday(5,1), "WORKERS_DAY");
        publicHolidays.put(new NationalPublicHoliday(5,25), "AFRICA_DAY");
        publicHolidays.put(new NationalPublicHoliday(6,16), "YOUTH_DAY");
        publicHolidays.put(new NationalPublicHoliday(7,18), "NELSON_MANDELAS_BIRTHDAY");
        publicHolidays.put(new NationalPublicHoliday(8,9), "WOMENS_DAY");
        publicHolidays.put(new NationalPublicHoliday(9,24), "HERITAGE_DAY");
        publicHolidays.put(new NationalPublicHoliday(12,16), "RECONCILIATION_DAY");
        publicHolidays.put(new NationalPublicHoliday(12,25), "CHRISTMAS_DAY");
        publicHolidays.put(new NationalPublicHoliday(12,26), "DAY_OF_GOODWILL");
    }

    public static boolean isPublicHoliday(NationalPublicHoliday enquiry){
        String holiday = publicHolidays.get(enquiry);
        logger.debug(new StringBuilder("Holiday enquired=").append(holiday).toString());
        return holiday != null && !holiday.trim().isEmpty();
    }

    public NationalPublicHoliday(int month, int day){
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        return new StringBuilder(this.getClass().getName()).append("[month=").append(this.month).
                append("],day=[").append(this.day).append("]").toString();
    }
}
