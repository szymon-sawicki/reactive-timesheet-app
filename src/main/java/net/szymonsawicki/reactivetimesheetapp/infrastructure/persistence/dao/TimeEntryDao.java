package net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.dao;

import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.TimeEntryEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface TimeEntryDao extends ReactiveMongoRepository<TimeEntryEntity, String> {
    Flux<TimeEntryEntity> findAllByUserAndDate(User user, LocalDate date);
    Flux<User> findAllByUser(User user);
}
