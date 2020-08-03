package za.recomed.businessduration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DurationServiceTest {

    @Autowired
    private DurationService durationService;

    @Test
    void contextLoads() {
        assertThat(durationService).isNotNull();
    }

    @Test
    void testCalculateZeroDuration() {
        String startTime = "2020-04-14T11:30:00"; //12600
        String endTime = "2020-04-14T11:30:00"; //19800

        assertThat(durationService.calculate(startTime,endTime)).isZero();
    }

    @Test
    void testCalculateFullBusinessDay() {
        String startTime = "2020-04-14T08:00:00";
        String endTime = "2020-04-14T17:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(32400l);
    }

    @Test
    void testCalculateEndEarly() {
        String startTime = "2020-04-14T08:00:00";
        String endTime = "2020-04-14T13:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(18000l);
    }

    @Test
    void testCalculateStartLate() {
        String startTime = "2020-04-14T12:00:00";
        String endTime = "2020-04-14T17:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(18000l);
    }

    @Test
    void testCalculateStartEarly() {
        String startTime = "2020-04-14T06:00:00";
        String endTime = "2020-04-14T17:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(32400l);
    }

    @Test
    void testCalculateFinishLate() {
        String startTime = "2020-04-14T08:00:00";
        String endTime = "2020-04-14T19:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(32400l);
    }

    @Test
    void testCalculateStartEarlyFinishLate() {
        String startTime = "2020-04-14T06:00:00";
        String endTime = "2020-04-14T19:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(32400l);
    }

    @Test
    void testCalculateEntireDurationBeforeStart() {
        String startTime = "2020-04-14T05:30:00";
        String endTime = "2020-04-14T07:30:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(0);
    }

    @Test
    void testCalculateEntireDurationAfterEnd() {
        String startTime = "2020-04-14T17:30:00";
        String endTime = "2020-04-14T19:30:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(0);
    }

    @Test
    void testCalculateTwoFullDays() {
        String startTime = "2020-04-14T08:00:00";
        String endTime = "2020-04-15T17:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(64800l);
    }

    @Test
    void testCalculateTwoDaysStaggeredTimes1() {
        String startTime = "2020-04-14T15:00:00";
        String endTime = "2020-04-15T09:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(10800l);
    }

    @Test
    void testCalculateTwoDaysStaggeredTimes2() {
        String startTime = "2020-04-14T11:00:00";
        String endTime = "2020-04-15T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(43200l);
    }

    @Test
    void testCalculateTwoDaysStaggeredTimes3() {
        String startTime = "2020-04-14T18:00:00";
        String endTime = "2020-04-15T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(21600l);
    }

    @Test
    void testCalculateThreeDaysStaggeredTimes1() {
        String startTime = "2020-04-14T15:00:00";
        String endTime = "2020-04-16T09:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(43200l);
    }

    @Test
    void testCalculateThreeDaysStaggeredTimes2() {
        String startTime = "2020-04-14T11:00:00";
        String endTime = "2020-04-16T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(75600l);
    }

    @Test
    void testCalculateThroughWeekend() {
        String startTime = "2020-04-17T11:00:00";
        String endTime = "2020-04-20T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(43200l);
    }

    @Test
    void testCalculateUntilSaturday() {
        String startTime = "2020-04-17T11:00:00";
        String endTime = "2020-04-18T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(21600l);
    }

    @Test
    void testCalculateUntilSunday() {
        String startTime = "2020-04-17T11:00:00";
        String endTime = "2020-04-19T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(21600l);
    }

    @Test
    void testCalculateFromSaturday() {
        String startTime = "2020-04-18T11:00:00";
        String endTime = "2020-04-21T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(54000l);
    }

    @Test
    void testCalculateFromSunday() {
        String startTime = "2020-04-19T11:00:00";
        String endTime = "2020-04-21T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(54000l);
    }

    @Test
    void testCalculateThroughNationalHoliday() {
        String startTime = "2020-06-15T11:00:00";
        String endTime = "2020-06-17T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(43200l);
    }

    @Test
    void testCalculateThroughObservedNationalHoliday() {
        String startTime = "2020-08-07T11:00:00";
        String endTime = "2020-08-11T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(43200l);
    }

    @Test
    void testCalculateOnlyOnWeekend() {
        String startTime = "2020-04-18T11:00:00";
        String endTime = "2020-04-19T14:00:00";

        assertThat(durationService.calculate(startTime,endTime)).isEqualTo(0);
    }

}