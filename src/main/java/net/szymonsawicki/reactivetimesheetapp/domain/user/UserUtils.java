package net.szymonsawicki.reactivetimesheetapp.domain.user;

import java.util.function.Function;

public interface UserUtils {

    Function<User, String> toId = user -> user.id;
    Function<User, String> toUsername = user -> user.username;
    Function<User, String> toTeamId = team -> team.teamId;
}
