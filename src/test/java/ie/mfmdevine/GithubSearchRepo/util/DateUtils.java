package ie.mfmdevine.GithubSearchRepo.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static Date getDateFromIsoString(CharSequence isoString) {
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(isoString);
        Instant i = Instant.from(ta);
        return Date.from(i);
    }

    public static Date getLastDateOfYear(int year) {
        return new GregorianCalendar(year, Calendar.DECEMBER, 31, 23, 59, 59).getTime();
    }
}
