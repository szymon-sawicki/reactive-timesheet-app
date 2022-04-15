package net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.type.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "time_entries")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntryEntity {

    @Id
    String id;

    LocalDateTime timeFrom;
    LocalDateTime timeTo;
    UserEntity user;
    Category category;
    String description;

    public TimeEntry toTimeEntry() {
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
