package net.szymonsawicki.reactivetimesheetapp.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Mono<GetTimeEntryDto> createTimeEntry(Mono<CreateTimeEntryDto> createTimeEntryDtoMono) {
        return createTimeEntryDtoMono
                .flatMap(createTimeEntryDto -> userRepository
                        .findById(createTimeEntryDto.user()))
    }

}
