package net.szymonsawicki.reactivetimesheetapp.web.handler;


import lombok.RequiredArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.application.service.TimeEntryService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeEntryHandler {
    private final TimeEntryService timeEntryService;
}
