package net.szymonsawicki.reactivetimesheetapp.domain.user;

import java.util.function.Function;

public interface UserUtils {
    Function<User, String> toUsername = user -> user.username;
}
