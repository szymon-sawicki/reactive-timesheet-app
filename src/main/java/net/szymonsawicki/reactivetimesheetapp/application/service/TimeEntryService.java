package net.szymonsawicki.reactivetimesheetapp.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.szymonsawicki.reactivetimesheetapp.application.service.exception.TimeEntryServiceException;
import net.szymonsawicki.reactivetimesheetapp.application.service.exception.UserServiceException;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.TimeEntry;
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
                .then(checkEntry(createTimeEntryDtoMono))
                .flatMap(createTimeEntryDto -> {
                    return timeEntryRepository.save(createTimeEntryDto.toTimeEntry())
                            .map(TimeEntry::toGetTimeEntryDto);
                });
    }

    private Mono<CreateTimeEntryDto> checkEntry(Mono<CreateTimeEntryDto> timeEntryToCheck) {
        return timeEntryToCheck.flatMap(t ->

                // at first check if the user exists

                userRepository
                        .findById(t.user().id())
                        .hasElement()
                        .flatMap(isUserPresent -> Boolean.TRUE.equals(isUserPresent)
                                ?
                                Mono.just(timeEntryToCheck)
                                :
                                Mono.error(new UserServiceException("user not exists")))

                        // then collision check of the time entry

                        .flatMap(entry -> timeEntryRepository.timeCheck(t.timeFrom(), t.timeTo())
                                .flatMap(result -> result
                                        ?
                                        Mono.error(new TimeEntryServiceException("time entry collision"))
                                        :
                                        Mono.just(t))));
    }
}
