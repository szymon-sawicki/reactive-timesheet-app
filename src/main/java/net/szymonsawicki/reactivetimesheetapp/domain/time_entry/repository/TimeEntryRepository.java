package net.szymonsawicki.reactivetimesheetapp.domain.time_entry.repository;

import net.szymonsawicki.reactivetimesheetapp.domain.configs.CrudRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.TimeEntryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface TimeEntryRepository extends CrudRepository<TimeEntry, String> {
    Flux<TimeEntry> findAllByUserAndDate(User user, LocalDate date);
    Flux<TimeEntry> findAllByUser(User user);
}