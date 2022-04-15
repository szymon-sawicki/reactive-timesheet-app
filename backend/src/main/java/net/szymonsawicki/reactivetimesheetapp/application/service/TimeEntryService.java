package net.szymonsawicki.reactivetimesheetapp.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.szymonsawicki.reactivetimesheetapp.application.service.exception.TimeEntryServiceException;
import net.szymonsawicki.reactivetimesheetapp.application.service.exception.UserServiceException;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntryUtils;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.dto.CreateTimeEntryDto;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.dto.GetTimeEntryDto;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.repository.TimeEntryRepository;
import net.szymonsawicki.reactivetimesheetapp.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final UserRepository userRepository;

    public Mono<GetTimeEntryDto> addTimeEntry(Mono<CreateTimeEntryDto> createTimeEntryDtoMono) {
        return createTimeEntryDtoMono
                .flatMap(this::checkEntry)
                .flatMap(createTimeEntryDto -> timeEntryRepository.save(createTimeEntryDto.toTimeEntry())
                        .map(TimeEntry::toGetTimeEntryDto));
    }

    private Mono<CreateTimeEntryDto> checkEntry(CreateTimeEntryDto timeEntryToCheck) {

        return userRepository
                .findById(timeEntryToCheck.user().id())
                .hasElement()
                .flatMap(isUserPresent -> Boolean.TRUE.equals(isUserPresent)
                        ?
                        findCollisions(timeEntryToCheck)
                        :
                        Mono.error(new UserServiceException("user not exists")));
    }

    private Mono<CreateTimeEntryDto> findCollisions(CreateTimeEntryDto timeEntryToCheck) {
        return timeEntryRepository.findAllByUser(timeEntryToCheck.user().toUser())
                .filter(entry -> !TimeEntryUtils.toTimeFrom.apply(entry).isAfter(timeEntryToCheck.timeTo())
                        && !TimeEntryUtils.toTimeTo.apply(entry).isBefore(timeEntryToCheck.timeFrom()))
                .collectList()
                .flatMap(result -> {
                    if (result.isEmpty()) {
                        Mono.error(new TimeEntryServiceException("time entry collision"));
                    }
                    return Mono.just(timeEntryToCheck);
                });
    }
}
