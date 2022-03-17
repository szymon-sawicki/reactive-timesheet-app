package net.szymonsawicki.reactivetimesheetapp.domain.time_entry.dto;

import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.type.Category;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.GetUserDto;

import java.time.LocalDate;
import java.time.LocalTime;

public record GetTimeEntryDto(String id, LocalDate date, LocalTime timeFrom, LocalTime timeTo, GetUserDto user, Category category, String description) {
    TimeEntry toTimeEntry() {
        return TimeEntry.builder()
                .id(id)
                .date(date)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .user(user.toUser())
                .category(category)
                .description(description)
                .build();
    }
}
