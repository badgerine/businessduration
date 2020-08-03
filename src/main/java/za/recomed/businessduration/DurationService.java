package za.recomed.businessduration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class DurationService {
    static Logger logger = LoggerFactory.getLogger(DurationService.class);
    private static final int BUSINESS_HOURS_DURATION = 9;
    private static final long BUSINESS_SECONDS_DURATION = BUSINESS_HOURS_DURATION * 60 *60;

    public Long calculate(String startTime, String endTime) {
        logger.debug("calculate");
        LocalDateTime start = parseDate(startTime);
        LocalDateTime end = parseDate(endTime);
        logger.debug(new StringBuilder("parsed datetime values | ").append("start=").append(start).
                append(" end=").append(end).toString());

        long forgoneStartTime = calculateForgoneStartTime(start);
        long forgoneEndTime = calculateForgoneEndTime(end);

        long duration = -forgoneStartTime -forgoneEndTime;

        for(LocalDateTime date = start; date.isBefore(end) || date.isEqual(end); date = date.plusDays(1).withHour(0)){
            duration += isBusinessDay(date.toLocalDate()) ? BUSINESS_SECONDS_DURATION : 0;
            logger.debug(new StringBuilder("for date=").append(date).toString());
        }

        return duration;
    }

    private LocalDateTime parseDate(String inputDate) {
        return LocalDateTime.parse(inputDate);
    }

    private boolean isBusinessDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        boolean isBusinessDay = dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY ;
//        isBusinessDay = isBusinessDay && !isNationalHolidayObserved(date);
        return isBusinessDay;
    }

    private boolean isNationalHolidayObserved(LocalDate localDate) {
        boolean observed;
//        if(localDate.getDayOfWeek() == DayOfWeek.MONDAY){
//            observed = holidayObservedMonday(localDate.minusDays(1));
//        }
        observed = NationalPublicHoliday.isPublicHoliday(new NationalPublicHoliday(localDate.getMonthValue(), localDate.getDayOfMonth()));
        return observed;
    }

    private boolean holidayObservedMonday(LocalDate localDate){
        return isNationalHolidayObserved(localDate);
    }

    private boolean isBusinessTime(LocalDateTime dateTime) {
        boolean isBeforeBusinessHours = 0 < dateTime.toLocalTime().compareTo(LocalTime.of(8,0,0));
        boolean isAfterBusinessHours = 0 > dateTime.toLocalTime().compareTo(LocalTime.of(17,0,0));
        return !isBeforeBusinessHours && !isAfterBusinessHours;
    }

    private long calculateForgoneStartTime(LocalDateTime dateTime) {
        long forgoneTime = LocalTime.of(8,0,0).until(dateTime.toLocalTime(), ChronoUnit.SECONDS);
        logger.debug(new StringBuilder("calculating start time offset=").append(forgoneTime).toString());
        return forgoneTime < 0 || forgoneTime >= BUSINESS_SECONDS_DURATION ? 0 : forgoneTime;
    }

    private long calculateForgoneEndTime(LocalDateTime dateTime) {
        long forgoneTime =dateTime.toLocalTime().until(LocalTime.of(17,0,0), ChronoUnit.SECONDS);
        logger.debug(new StringBuilder("calculating end time offset=").append(forgoneTime).toString());
        return forgoneTime < 0 || forgoneTime >= BUSINESS_SECONDS_DURATION ? 0 : forgoneTime;
    }

}