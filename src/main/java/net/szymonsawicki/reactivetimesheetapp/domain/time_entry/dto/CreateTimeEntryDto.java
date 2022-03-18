package net.szymonsawicki.reactivetimesheetapp.domain.time_entry.dto;

import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.type.Category;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.GetUserDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateTimeEntryDto(LocalDate date, LocalTime timeFrom, LocalTime timeTo, GetUserDto user, Category category, String description) {
    public TimeEntry toTimeEntry() {
        return TimeEntry.builder()
                .date(date)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .user(user.toUser())
                .category(category)
                .description(description)
                .build();
    }
}
