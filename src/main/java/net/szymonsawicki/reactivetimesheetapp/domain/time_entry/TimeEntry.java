package net.szymonsawicki.reactivetimesheetapp.domain.time_entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.dto.GetTimeEntryDto;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.type.Category;

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
    Category category;
    String description;

    GetTimeEntryDto toGetTimeEntryDto() {
        return new GetTimeEntryDto(
                id,
                date,
                timeFrom,
                timeTo,
                category,
                description);
    }
}
