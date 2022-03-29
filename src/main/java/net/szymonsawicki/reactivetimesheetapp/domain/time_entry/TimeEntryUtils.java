package net.szymonsawicki.reactivetimesheetapp.domain.time_entry;

import java.time.LocalDateTime;
import java.util.function.Function;

public interface TimeEntryUtils {

    Function<TimeEntry, LocalDateTime> toTimeFrom = timeEntry -> timeEntry.timeFrom;
    Function<TimeEntry, LocalDateTime> toTimeTo = timeEntry -> timeEntry.timeTo;

}
