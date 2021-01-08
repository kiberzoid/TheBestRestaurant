package ru.bestrestaurant.util;

import ru.bestrestaurant.util.exception.BadParametersException;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeUtil {

    public static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);
    public static final LocalTime THRESHOLD_TIME = LocalTime.of(11,0,0);

    private DateTimeUtil() {

    }

    public static LocalDate[] interval(LocalDate fromD, LocalDate toD) {
        LocalDate[] period = new LocalDate[2];
        period[0] = fromD == null && toD == null ? LocalDate.now() :
                fromD == null ? MIN_DATE : fromD;
        period[1] = toD == null ? LocalDate.now() : toD;
        if(period[0].isAfter(period[1]))
            throw new BadParametersException("Start of period can't be after end of this period");
        return period;
    }

    public static boolean isThresholdExceeded(){
        return LocalTime.now().isAfter(THRESHOLD_TIME);
    }

}
