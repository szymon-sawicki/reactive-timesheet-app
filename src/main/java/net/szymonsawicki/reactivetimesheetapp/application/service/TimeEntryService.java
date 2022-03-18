package net.szymonsawicki.reactivetimesheetapp.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.szymonsawicki.reactivetimesheetapp.application.service.exception.TimeEntryServiceException;
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
                .flatMap(createTimeEntryDto -> {

                    // check if user from time entry exists in db

                    userRepository
                            .findById(createTimeEntryDto.user().id())
                            .switchIfEmpty(Mono.error(new TimeEntryServiceException("cannot find user")));

                    // call of the private method, which checks in db if there is a conflict with existing entries

                    checkTime(createTimeEntryDto);

                    return timeEntryRepository.save(createTimeEntryDto.toTimeEntry())
                            .map(TimeEntry::toGetTimeEntryDto);
                });
    }

    private void checkTime(CreateTimeEntryDto timeEntryToCheck) {
        timeEntryRepository.findAllByUserAndDate(timeEntryToCheck.user().toUser(), timeEntryToCheck.date())
                .filter(timeEntry ->
                        TimeEntryUtils.toTimeFrom.apply(timeEntry).isAfter(timeEntryToCheck.timeTo()) ||
                                TimeEntryUtils.toTimeTo.apply(timeEntry).isBefore(timeEntryToCheck.timeFrom()))
                .switchIfEmpty(Mono.error(new TimeEntryServiceException("entry time conflict")));
    }
}
