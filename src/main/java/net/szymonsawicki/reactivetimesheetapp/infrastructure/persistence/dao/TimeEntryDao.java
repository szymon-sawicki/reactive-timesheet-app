package net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.dao;

import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TimeEntryDao extends ReactiveMongoRepository<TimeEntry, String> {
    // TODO custom methods
}
