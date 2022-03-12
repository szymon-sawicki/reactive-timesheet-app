package net.szymonsawicki.reactivetimesheetapp.domain.team;

import java.util.function.Function;

public interface TeamUtils {

    Function<Team, String> toId = team -> team.id;
}
