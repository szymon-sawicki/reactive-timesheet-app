package net.szymonsawicki.reactivetimesheetapp.domain.time_entry.dto;

import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.type.Category;

import java.time.LocalDate;
import java.time.LocalTime;

public record GetTimeEntryDto(String id, LocalDate date, LocalTime timeFrom, LocalTime timeTo, Category category, String description) {
    TimeEntry toTimeEntry() {
        return TimeEntry.builder()
                .id(id)
                .date(date)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .category(category)
                .description(description)
                .build();
    }
}
