package net.szymonsawicki.reactivetimesheetapp.domain.team.dto;

import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.user.User;

import java.util.List;

public record CreateTeamDto(String name, User lead, List<User> members) {
    public Team toTeam() {
        return Team.builder()
                .name(name)
                .lead(lead)
                .members(members)
                .build();
    }
}
