package net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.dao;

import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.TimeEntryEntity;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface TimeEntryDao extends ReactiveMongoRepository<TimeEntryEntity, String> {
    Flux<TimeEntryEntity> findAllByUserAndDate(UserEntity user, LocalDate date);
    Flux<TimeEntryEntity> findAllByUser(UserEntity user);
}
