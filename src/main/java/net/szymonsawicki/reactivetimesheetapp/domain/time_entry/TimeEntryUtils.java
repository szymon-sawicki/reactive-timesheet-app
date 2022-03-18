package net.szymonsawicki.reactivetimesheetapp.domain.time_entry;

import java.time.LocalTime;
import java.util.function.Function;

public interface TimeEntryUtils {

    Function<TimeEntry, LocalTime> toTimeFrom = timeEntry -> timeEntry.timeFrom;
    Function<TimeEntry, LocalTime> toTimeTo = timeEntry -> timeEntry.timeTo;

}
