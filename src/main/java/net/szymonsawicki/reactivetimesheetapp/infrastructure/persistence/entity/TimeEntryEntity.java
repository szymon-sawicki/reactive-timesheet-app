package net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.type.Category;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "time_entries")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntryEntity {

    @Id
    String id;

    LocalDate date;
    LocalTime timeFrom;
    LocalTime timeTo;
    User user;
    Category category;
    String description;

    TimeEntry toTimeEntry() {
        return TimeEntry.builder()
                .id(id)
                .date(date)
                .timeFrom(timeFrom)
                .timeTo(timeTo)
                .user(user)
                .category(category)
                .description(description)
                .build();
    }
}
