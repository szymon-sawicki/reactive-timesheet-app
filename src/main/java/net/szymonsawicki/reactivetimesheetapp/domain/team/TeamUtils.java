package net.szymonsawicki.reactivetimesheetapp.domain.team;

import net.szymonsawicki.reactivetimesheetapp.domain.user.User;

import java.util.List;
import java.util.function.Function;

public interface TeamUtils {

    Function<Team, String> toId = team -> team.id;
    Function<Team, String> toName = team -> team.name;
    Function<Team, List<User>> toMembers = team -> team.members;
}
