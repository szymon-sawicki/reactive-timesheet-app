package net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.repository.TimeEntryRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.dao.TimeEntryDao;
import net.szymonsawicki.reactivetimesheetapp.infrastructure.persistence.exception.PersistenceException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeEntryRepositoryImpl implements TimeEntryRepository {
    
    private final TimeEntryDao timeEntryDao;


    @Override
    public Flux<TimeEntry> findAll() {
        return timeEntryDao.findAll()
                .flatMap(timeEntry -> Mono.just(timeEntry.toTimeEntry()));
    }

    @Override
    public Flux<TimeEntry> findAllById(List<String> ids) {
        return timeEntryDao.findAllById(ids)
                .flatMap(timeEntry -> Mono.just(timeEntry.toTimeEntry()));
    }

    @Override
    public Mono<TimeEntry> findById(String id) {
        return timeEntryDao.findById(id)
                .flatMap(timeEntry -> Mono.just(timeEntry.toTimeEntry()));
    }

    @Override
    public Mono<TimeEntry> save(TimeEntry timeEntry) {

        return timeEntryDao.save(timeEntry.toEntity())
                .flatMap(timeEntryEntity -> Mono.just(timeEntryEntity.toTimeEntry()));
    }

    @Override
    public Mono<TimeEntry> delete(String id) {

        return timeEntryDao.findById(id)
                .flatMap(timeEntryEntity -> timeEntryDao.delete(timeEntryEntity)
                        .then(Mono.just(timeEntryEntity.toTimeEntry())))
                .switchIfEmpty(Mono.error(new PersistenceException("cannot find team to delete")));
    }

    @Override
    public Flux<TimeEntry> findAllByUserAndDate(User user, LocalDate date) {
        return timeEntryDao.findAllByUserAndDate(user.toEntity(),date)
                .flatMap(timeEntryEntity -> Mono.just(timeEntryEntity.toTimeEntry()));
    }

    @Override
    public Flux<TimeEntry> findAllByUser(User user) {
        return timeEntryDao.findAllByUser(user.toEntity())
                .flatMap(timeEntryEntity -> Mono.just(timeEntryEntity.toTimeEntry()));
    }
}
