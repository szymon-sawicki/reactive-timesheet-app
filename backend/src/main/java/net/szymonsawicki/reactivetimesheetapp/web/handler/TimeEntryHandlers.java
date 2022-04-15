package net.szymonsawicki.reactivetimesheetapp.web.handler;


import lombok.RequiredArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.application.service.TimeEntryService;
import net.szymonsawicki.reactivetimesheetapp.domain.time_entry.dto.CreateTimeEntryDto;
import net.szymonsawicki.reactivetimesheetapp.web.config.GlobalRoutingHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TimeEntryHandlers {
    private final TimeEntryService timeEntryService;

    public Mono<ServerResponse> addTimeEntry(ServerRequest serverRequest) {
        var createTimeEntryDtoMono = serverRequest.bodyToMono(CreateTimeEntryDto.class);
        return GlobalRoutingHandler.doRequest(timeEntryService.addTimeEntry(createTimeEntryDtoMono), HttpStatus.CREATED);
    }
}
