package za.recomed.businessduration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        long maxTimeFromStart = calculateMaxIntervalFromStart(start);
        long maxTimeToEnd = calculateMaxIntervalToEnd(end);

        long duration = 0;

        if(isBusinessDay(start.toLocalDate())){
            duration += maxTimeFromStart;
            if(start.toLocalDate().isEqual(end.toLocalDate())){
                duration -= BUSINESS_SECONDS_DURATION;
            }
        }
        if(isBusinessDay(end.toLocalDate())){
            duration += maxTimeToEnd;
        }
        logger.debug(new StringBuilder("duration=").append(duration).toString());
        for(LocalDate date = start.toLocalDate().plusDays(1); date.isBefore(end.toLocalDate()); date = date.plusDays(1)){
            duration += isBusinessDay(date) ? BUSINESS_SECONDS_DURATION : 0;
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
        isBusinessDay = isBusinessDay && !isNationalHolidayObserved(date);
        return isBusinessDay;
    }

    private boolean isNationalHolidayObserved(LocalDate localDate) {
        boolean observed = false;
        if(localDate.getDayOfWeek() == DayOfWeek.MONDAY){
            observed = NationalPublicHoliday.isPublicHoliday(localDate.minusDays(1));
        }
        observed = observed || NationalPublicHoliday.isPublicHoliday(localDate);
        return observed;
    }

    private boolean isBusinessTime(LocalDateTime dateTime) {
        boolean isBeforeBusinessHours = 0 < dateTime.toLocalTime().compareTo(LocalTime.of(8,0,0));
        boolean isAfterBusinessHours = 0 > dateTime.toLocalTime().compareTo(LocalTime.of(17,0,0));
        return !isBeforeBusinessHours && !isAfterBusinessHours;
    }

    private long calculateMaxIntervalFromStart(LocalDateTime dateTime) {
        long maxStartInterval =dateTime.toLocalTime().until(LocalTime.of(17,0,0), ChronoUnit.SECONDS);
        logger.debug(new StringBuilder("calculating max start time interval=").append(maxStartInterval).toString());
        return maxStartInterval < 0 ? 0 : (maxStartInterval >= BUSINESS_SECONDS_DURATION ? BUSINESS_SECONDS_DURATION : maxStartInterval);
    }

    private long calculateMaxIntervalToEnd(LocalDateTime dateTime) {
        long maxEndInterval = LocalTime.of(8,0,0).until(dateTime.toLocalTime(), ChronoUnit.SECONDS);
        logger.debug(new StringBuilder("calculating max end time interval=").append(maxEndInterval).toString());
        return maxEndInterval < 0 ? 0 : (maxEndInterval >= BUSINESS_SECONDS_DURATION ? BUSINESS_SECONDS_DURATION : maxEndInterval);
    }

}