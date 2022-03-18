package net.szymonsawicki.reactivetimesheetapp.web.handler;

import lombok.RequiredArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.application.service.TeamService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamHandler {
    private final TeamService teamService;
}
