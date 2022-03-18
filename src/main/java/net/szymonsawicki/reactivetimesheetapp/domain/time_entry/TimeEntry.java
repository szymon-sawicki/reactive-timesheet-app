package net.szymonsawicki.reactivetimesheetapp.domain.time_entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.dto.GetTimeEntryDto;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.type.Category;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.TimeEntryEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class TimeEntry {

    String id;
    LocalDate date;
    LocalTime timeFrom;
    LocalTime timeTo;
    User user;
    Category category;
    String description;

    public TimeEntryEntity toEntity() {
        return TimeEntryEntity.builder()
                .id(id)
                .date(date)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .user(user.toEntity())
                .category(category)
                .description(description)
                .build();
    }

    public GetTimeEntryDto toGetTimeEntryDto() {
        return new GetTimeEntryDto(
                id,
                date,
                timeFrom,
                timeTo,
                user.toGetUserDto(),
                category,
                description);
    }
}
