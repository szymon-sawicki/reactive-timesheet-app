package net.szymonsawicki.reactivetimesheetapp.domain.time_entry.dto;

import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.type.Category;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.GetUserDto;

public record GetTimeEntryDto(String id, java.time.LocalDateTime timeFrom, java.time.LocalDateTime timeTo, GetUserDto user, Category category, String description) {
    TimeEntry toTimeEntry() {
        return TimeEntry.builder()
                .id(id)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .user(user.toUser())
                .category(category)
                .description(description)
                .build();
    }
}
