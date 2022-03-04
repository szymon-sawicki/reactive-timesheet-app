package net.szymonsawicki.reactivetimesheetapp.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.szymonsawicki.reactivetimesheetapp.domain.user.dto.GetUserDto;
import net.szymonsawicki.reactivetimesheetapp.domain.user.type.Role;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class User {

    String id;
    String username;
    String password;
    Role role;
    String teamId;

    GetUserDto toGetUserDto() {
        return new GetUserDto(
                id,
                username,
                password,
                role,
                teamId);
    }
}
