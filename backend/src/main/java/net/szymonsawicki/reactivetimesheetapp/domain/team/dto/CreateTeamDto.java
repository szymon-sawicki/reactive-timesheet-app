package net.szymonsawicki.reactivetimesheetapp.domain.team.dto;

import net.szymonsawicki.reactivetimesheetapp.domain.team.Team;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.GetUserDto;

import java.util.List;

public record CreateTeamDto(String name, List<GetUserDto> members) {
    public Team toTeam() {
        return Team.builder()
                .name(name)
                .members(members.stream().map(GetUserDto::toUser).toList())
                .build();
    }
}
